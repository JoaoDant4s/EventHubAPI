import { Link, useNavigate } from "react-router-dom";
import Title from "../../../components/Title";
import Button from "../../../components/Button";
import { faAddressCard, faArrowRight, faCalendarDays, faChevronRight, faComment, faPhone, faUser, faEnvelope, faLock } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { FormEvent, useEffect, useState } from "react";
import Alert, { getAlert, setAlert, Status } from "../../../components/Alert";
import { apiAttractionUpdateInfo, apiGetAttractionById, apiGetByEmail, apiParticipantUpdateInfo, AttractionDTO, AttractionInfoDTO, ParticipantInfoDTO } from "../../../api/services/user";
import { UserDTO } from "../../../api/services/user";
import { PatternFormat } from 'react-number-format';
import { Role } from "../../../../main";

export default function AttractionRegistration() {
  const navigate = useNavigate();
  const [user,setUser] = useState<UserDTO>();
  const [attraction, setAttraction] = useState<AttractionDTO>();
  const [role, setRole] = useState<Role>("participant");
  
  const [name, setName] = useState<String>("");
  const [email, setEmail] = useState<String>("");
  const [cpf, setCpf] = useState<String>("");
  const [birthDate, setBirthDate] = useState<String>("");
  const [description, setDescription] = useState<String>("");
  const [contact, setContact] = useState<String>("");

  //ALERT STATES
  const [message, setMessage] = useState<String>("");
  const [status, setStatus] = useState<Status>("success");
  const [visible, setVisible] = useState<boolean>(false);
  const [title, setTitle] = useState<String>("Sucesso!");
  
  const saveUser=async (e: FormEvent<HTMLFormElement>)=> {
    e.preventDefault();
    
    if(role === "attraction"){
      const attractionInfo:AttractionInfoDTO = {
        id:Number(attraction?.id),
        name:name.trim(),
        cpf:cpf.trim(),
        birthDate:birthDate.trim(),
        description:description.trim(),
        contact:contact.trim(),
      }
  
      const response = await apiAttractionUpdateInfo(attractionInfo)
      .then((response)=>{
        const data = response?.data;
        console.log(data);
        setAlert("Informações atualizadas com sucesso!", "success", true);
        navigate("/dashboard/profile");
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
        id:Number(user?.id),
        name:name.trim(),
        cpf:cpf.trim(),
        birthDate:birthDate.trim(),
      }
  
      const response = await apiParticipantUpdateInfo(participantInfo)
      .then((response)=>{
        const data = response?.data;
        
        setAlert("Informações atualizadas com sucesso!", "success", true);
        navigate("/dashboard/profile");
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
    const email = localStorage.getItem("login") || "";
    const response = await apiGetByEmail(email)
    .then((response)=>{
      const data:UserDTO = response?.data;
      setName(data.name);
      setCpf(data.cpf);
      setBirthDate(data.birthDate);
      setUser(data);
    });
  }

  useEffect(()=>{
    setUserData();
    getAlert(setMessage, setStatus, setVisible, setTitle);
    setRole(localStorage.getItem("role") || "participant");
  }, [])

  useEffect(()=>{
    if(role === "attraction"){
      setAttraction(JSON.parse(localStorage.getItem("attraction") || ""))
    }
  }, [role])
  
  return (
    <>
        <div className=" flex justify-between items-center mb-10">
            <div className='flex items-end'>
                <Title>Cadastrar atração</Title>
                <div className=" flex items-end ">
                  <FontAwesomeIcon icon={faChevronRight} className=' pl-3 text-font-text text-[0.6rem] mb-4 ' />
                  <Link to="/admin/users" className=' pl-3 text-font-text text-[0.8rem] mb-[0.6rem]'>Voltar</Link>
                </div>
            </div>
        </div>
        <form onSubmit={(e)=>saveUser(e)} className=" w-full flex flex-col gap-8 ">
          <div className=" flex flex-col">
            <p className=" font-bold text-font-input ml-3 mb-2 ">Nome</p>
            <label className=" flex items-center w-full mb-5">
              <FontAwesomeIcon icon={faUser} className=' absolute pl-3 text-font-icon text-sm' />
              <input onChange={(e)=>setName(e.target.value)} className=" bg-bg-white w-[350px] p-2 pl-10 rounded-md placeholder-font-placeholder text-font-input text-sm shadow-sm " type="text" name="name" id="name" placeholder="Nome" />
            </label>
            <p className=" font-bold text-font-input ml-3 mb-2 ">E-mail</p>
            <label className=" flex items-center w-full mb-5">
              <FontAwesomeIcon icon={faEnvelope} className=' absolute pl-3 text-font-icon text-sm' />
              <input onChange={(e)=>setEmail(e.target.value)} className=" bg-bg-white w-[350px] p-2 pl-10 rounded-md placeholder-font-placeholder text-font-input text-sm shadow-sm " type="text" name="email" id="email" placeholder="E-mail" />
            </label>
            <p className=" font-bold text-font-input ml-3 mb-2 ">CPF</p>
            <label className=" flex items-center w-full mb-5 ">
              <FontAwesomeIcon icon={faAddressCard} className=' absolute pl-3 text-font-icon text-sm' />
              <PatternFormat format="###.###.###-##" mask={"_"} allowEmptyFormatting onChange={(e) => setCpf(e.target.value)} className=" bg-bg-white w-[350px] p-2 pl-10 rounded-md placeholder-font-placeholder text-font-input text-sm shadow-sm " type="text" name="cpf" id="cpf" value={user?.cpf.toString()} placeholder="CPF" />
            </label>
            <p className=" font-bold text-font-input ml-3 mb-2 ">Data de nascimento</p>
            <label className=" flex items-center w-full mb-5 last:mb-0 ">
              <FontAwesomeIcon icon={faCalendarDays} className=' absolute pl-3 text-font-icon text-sm' />
              <input onChange={(e)=>setBirthDate(e.target.value)} className=" bg-bg-white w-[350px] p-2 pl-10 rounded-md placeholder-font-placeholder text-font-placeholder text-sm shadow-sm " type="date" name="birthDate" id="birthDate" defaultValue={user?.birthDate.toString()} placeholder="Data de nascimento" />
            </label>
            <p className=" font-bold text-font-input ml-3 mb-2 ">Senha</p>
            <label className=" flex items-center w-full mb-5">
              <FontAwesomeIcon icon={faLock} className=' absolute pl-3 text-font-icon text-sm' />
              <input onChange={(e)=>setName(e.target.value)} className=" bg-bg-white w-[350px] p-2 pl-10 rounded-md placeholder-font-placeholder text-font-input text-sm shadow-sm " type="text" name="password" id="password" placeholder="Senha" />
            </label>
            <p className=" font-bold text-font-input ml-3 mb-2 ">Confirmação de senha</p>
            <label className=" flex items-center w-full mb-5">
              <FontAwesomeIcon icon={faLock} className=' absolute pl-3 text-font-icon text-sm' />
              <input onChange={(e)=>setName(e.target.value)} className=" bg-bg-white w-[350px] p-2 pl-10 rounded-md placeholder-font-placeholder text-font-input text-sm shadow-sm " type="text" name="confirmPassword" id="confirmPassword" placeholder="Confirmação de senha" />
            </label>
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

          </div>
          <div className=" grid grid-cols-2 w-full gap-4">
            <Button size="default" color='default' icon={faArrowRight} >Salvar</Button>
          </div>
        </form>
        <Alert status={status} visible={visible} setVisible={setVisible} title={title.toString()}>
            <p className='text-justify text-xs pl-4 mt-2 text-font-text'>{message}</p>
        </Alert>
    </>
  )
}
