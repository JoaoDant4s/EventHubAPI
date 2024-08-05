import { Link, useNavigate, useParams } from "react-router-dom";
import Title from "../../../components/Title";
import Button from "../../../components/Button";
import { faAddressCard, faArrowRight, faCalendarDays, faChevronRight, faComment, faPhone, faUser } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { FormEvent, useEffect, useState } from "react";
import Alert, { getAlert, setAlert, Status } from "../../../components/Alert";
import { apiAttractionUpdateInfo, apiGetAttractionById, apiGetByEmail, apiGetUserById, apiParticipantUpdateInfo, AttractionDTO, AttractionInfoDTO, ParticipantInfoDTO } from "../../../api/services/user";
import { UserDTO } from "../../../api/services/user";
import { PatternFormat } from 'react-number-format';
import { Role } from "../../../../main";

export default function UpdateParticipant() {
  const {id} = useParams();
  const navigate = useNavigate();

  const [user,setUser] = useState<UserDTO>();
  const [targetUser,setTargetUser] = useState<UserDTO>();
  const [role, setRole] = useState<Role>("participant");
  const [attraction, setAttraction] = useState<AttractionDTO>();
  
  const [name, setName] = useState<string>("");
  const [cpf, setCpf] = useState<string>("");
  const [birthDate, setBirthDate] = useState<string>("");
  const [description, setDescription] = useState<string>("");
  const [contact, setContact] = useState<string>("");

  //ALERT STATES
  const [message, setMessage] = useState<string>("");
  const [status, setStatus] = useState<Status>("success");
  const [visible, setVisible] = useState<boolean>(false);
  const [title, setTitle] = useState<string>("Sucesso!");
  
  const updateUser=async (e: FormEvent<HTMLFormElement>)=> {
    e.preventDefault();
    
    if(role === "attraction"){
      const attractionInfo:AttractionInfoDTO = {
        id:Number(targetUser?.attractionId),
        name:name.trim(),
        cpf:cpf.trim(),
        birthDate:birthDate.trim(),
        description:description.trim(),
        contact:contact.trim(),
      }
  
      const response = await apiAttractionUpdateInfo(attractionInfo)
      .then((response)=>{
        const data = response?.data;
        setAlert("Informações do usuário atualizadas com sucesso!", "success", true);
        navigate("/admin/users");
      })
      .catch((e)=>{
        if(e.response.data.detail == null){
          setAlert("Algo inesperado aconteceu", "alert", true);
        } else {
          setAlert(e.response.data.detail, "alert", true);
        }
        getAlert(setMessage, setStatus, setVisible, setTitle);
  
      });
    } else {
      const participantInfo:ParticipantInfoDTO = {
        id:Number(targetUser?.id),
        name:name.trim(),
        cpf:cpf.trim(),
        birthDate:birthDate.trim(),
      }
  
      const response = await apiParticipantUpdateInfo(participantInfo)
      .then((response)=>{
        const data = response?.data;
        
        setAlert("Informações do usuário atualizadas com sucesso!", "success", true);
        navigate("/admin/users");
      })
      .catch((e)=>{
        if(e.response.data.detail == null){
          setAlert("Algo inesperado aconteceu", "alert", true);
        } else {
          setAlert(e.response.data.detail, "alert", true);
        }
        getAlert(setMessage, setStatus, setVisible, setTitle);
  
      });
    }
  }

  const setUserData = async () =>{
    const login = localStorage.getItem("login") || "";
    await apiGetByEmail(login)
    .then((response)=>{
      const data = response?.data;
      setUser(data);
    });
  }

  const setTargetUserData = async () =>{
    if(Number(id)){
      let userId:Number = Number(id);
      await apiGetUserById(userId)
      .then((response)=>{
        const data:UserDTO = response?.data;
        setTargetUser(data);
        setName(data.name)
        setCpf(data.cpf)
        setBirthDate(data.birthDate)
      });
    }
  }
  
  const setAttractionData = async () =>{
    if(targetUser !== undefined && targetUser?.attractionId !== null){
      await apiGetAttractionById(targetUser?.attractionId)
      .then((response)=>{
        const data = response?.data;
        setAttraction(data);
      })
    }
  }

  useEffect(()=>{
    setUserData();
    getAlert(setMessage, setStatus, setVisible, setTitle);
    setRole(localStorage.getItem("role") || "participant");
  }, [])

  useEffect(()=>{
    setTargetUserData();
  }, [id])

  useEffect(()=>{
    setAttractionData();
  }, [targetUser])
  
  return (
    <>
        <div className=" flex justify-between items-center mb-10">
            <div className='flex items-end'>
                <Title>Editar Perfil</Title>
                <div className=" flex items-end ">
                  <FontAwesomeIcon icon={faChevronRight} className=' pl-3 text-font-text text-[0.6rem] mb-4 ' />
                  <Link to="/admin/users" className=' pl-3 text-font-text text-[0.8rem] mb-[0.6rem]'>Voltar</Link>
                </div>
            </div>
        </div>
        <form onSubmit={(e)=>updateUser(e)} className=" w-full flex flex-col gap-8 ">
        <div className=" flex flex-col">
            <p className=" font-bold text-font-input ml-3 mb-2 ">Nome</p>
            <label className=" flex items-center w-full mb-5">
              <FontAwesomeIcon icon={faUser} className=' absolute pl-3 text-font-icon text-sm' />
              <input onChange={(e)=>setName(e.target.value)} className=" bg-bg-white w-[350px] p-2 pl-10 rounded-md placeholder-font-placeholder text-font-input text-sm shadow-sm " type="text" name="name" id="name" defaultValue={targetUser?.name.toString()} placeholder="Nome" />
            </label>
            {
              targetUser?.promoter === true
              ? (
                <>
                  <p className=" font-bold text-font-input ml-3 mb-2 ">CNPJ</p>
                  <label className=" flex items-center w-full mb-5 ">
                    <FontAwesomeIcon icon={faAddressCard} className=' absolute pl-3 text-font-icon text-sm' />
                    <PatternFormat format="##.###.###/####-##" mask={"_"} onChange={(e) => setCpf(e.target.value)} className=" bg-bg-white w-[350px] p-2 pl-10 rounded-md placeholder-font-placeholder text-font-input text-sm shadow-sm " type="text" name="cpf" id="cpf" value={targetUser?.cpf.toString()} placeholder="CNPJ" />
                  </label>
                </>
              )
              : (
                <>
                  <p className=" font-bold text-font-input ml-3 mb-2 ">CPF</p>
                  <label className=" flex items-center w-full mb-5 ">
                    <FontAwesomeIcon icon={faAddressCard} className=' absolute pl-3 text-font-icon text-sm' />
                    <PatternFormat format="###.###.###-##" mask={"_"} onChange={(e) => setCpf(e.target.value)} className=" bg-bg-white w-[350px] p-2 pl-10 rounded-md placeholder-font-placeholder text-font-input text-sm shadow-sm " type="text" name="cpf" id="cpf" value={targetUser?.cpf.toString()} placeholder="CPF" />
                  </label>
                </>
              )
            }
            {
              targetUser?.promoter === true
              ? (
                <p className=" font-bold text-font-input ml-3 mb-2 ">Data de fundação</p>
              )
              : (
                <p className=" font-bold text-font-input ml-3 mb-2 ">Data de nascimento</p>
              )
            }
            <label className=" flex items-center w-full mb-5 last:mb-0 ">
              <FontAwesomeIcon icon={faCalendarDays} className=' absolute pl-3 text-font-icon text-sm' />
              <input onChange={(e)=>setBirthDate(e.target.value)} className=" bg-bg-white w-[350px] p-2 pl-10 rounded-md placeholder-font-placeholder text-font-placeholder text-sm shadow-sm " type="date" name="birthDate" id="birthDate" defaultValue={targetUser?.birthDate.toString()} placeholder="Data de nascimento" />
            </label>
            {
              targetUser?.attractionId !== null
              ? (
                <>
                  <p className=" font-bold text-font-input ml-3 mb-2 ">Contato</p>
                  <label className=" flex items-center w-full mb-5 last:mb-0 ">
                    <FontAwesomeIcon icon={faPhone} className=' absolute pl-3 text-font-icon text-sm' />
                    <PatternFormat format="(##) #####-####" mask={"_"} onChange={(e) => setContact(e.target.value)} className=" bg-bg-white w-[350px] p-2 pl-10 rounded-md placeholder-font-placeholder text-font-input text-sm shadow-sm " type="text" name="contact" id="contact" value={attraction?.contact.toString()} placeholder="Contato" />
                  </label>
                  <p className=" font-bold text-font-input ml-3 mb-2 ">Descrição</p>
                  <label className=" flex items-center w-full mb-5 last:mb-0 ">
                    <FontAwesomeIcon icon={faComment} className=' absolute pl-3 text-font-icon text-sm' />
                    <textarea onChange={(e)=>setDescription(e.target.value)} rows={4} className=" bg-bg-white w-[350px] p-2 pl-10 rounded-md placeholder-font-placeholder text-sm shadow-sm " name="description" id="description" defaultValue={attraction?.description.toString()} placeholder="Descrição" ></textarea>
                  </label>
                </>
              )
              : null
            }

          </div>
          <div className=" grid grid-cols-2 w-full gap-4">
            <Button type="submit" size="default" color='default' icon={faArrowRight} >Salvar</Button>
          </div>
        </form>
        <Alert status={status} visible={visible} setVisible={setVisible} title={title.toString()}>
            <p className='text-justify text-xs pl-4 mt-2 text-font-text'>{message}</p>
        </Alert>
    </>
  )
}
