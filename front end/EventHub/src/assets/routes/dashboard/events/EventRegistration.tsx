import { Link, useNavigate } from "react-router-dom";
import Title from "../../../components/Title";
import Button from "../../../components/Button";
import { faAddressCard, faArrowRight, faCalendarDays, faChevronRight, faComment, faPhone, faUser, faEnvelope, faLock, faTag, faUsers, faLocationDot } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { FormEvent, useEffect, useState } from "react";
import Alert, { getAlert, setAlert, Status } from "../../../components/Alert";
import { apiAttractionUpdateInfo, apiGetAttractionById, apiGetByEmail, apiParticipantRegistration, apiParticipantUpdateInfo, apiPromoterRegistration, AttractionDTO, AttractionInfoDTO, ParticipantInfoDTO, ParticipantRegistrationDTO } from "../../../api/services/user";
import { UserDTO } from "../../../api/services/user";
import { PatternFormat } from 'react-number-format';
import { Role } from "../../../../main";
import { apiEventRegistration, eventType, SaveEventDTO } from "../../../api/services/events";

export default function EventRegistration() {
  const navigate = useNavigate();
  const [user,setUser] = useState<UserDTO>();
  const [attraction, setAttraction] = useState<AttractionDTO>();
  const [role, setRole] = useState<Role>("participant");

  const [name, setName] = useState<string>("");
  const [address, setAddress] = useState<string>("");
  const [type, setType] = useState<eventType>("");
  const [initialDate, setInitialDate] = useState<string>("");
  const [finalDate, setFinalDate] = useState<string>("");
  const [maximumCapacity, setMaximumCapacity] = useState<number>(0);
  const [description, setDescription] = useState<string>("");

  //ALERT STATES
  const [message, setMessage] = useState<string>("");
  const [status, setStatus] = useState<Status>("success");
  const [visible, setVisible] = useState<boolean>(false);
  const [title, setTitle] = useState<string>("Sucesso!");
  
  const saveEvent=async (e: FormEvent<HTMLFormElement>)=> {
    e.preventDefault();
    
    const event:SaveEventDTO = {
      name: name,
      type: type,
      finalDate: finalDate,
      initialDate: initialDate,
      maximumCapacity: maximumCapacity,
      address: address,
      description: description
    }

    const response = await apiEventRegistration(event)
    .then((response)=>{
      const data = response?.data;
      console.log(data);
      setAlert("Promotor cadastrado com sucesso!", "success", true);
      navigate("/admin/events");
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

  const setUserData = async () =>{
    const email = localStorage.getItem("login") || "";
    const response = await apiGetByEmail(email)
    .then((response)=>{
      const data:UserDTO = response?.data;
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
                <Title>Cadastrar evento</Title>
                <div className=" flex items-end ">
                  <FontAwesomeIcon icon={faChevronRight} className=' pl-3 text-font-text text-[0.6rem] mb-4 ' />
                  <Link to="/admin/events" className=' pl-3 text-font-text text-[0.8rem] mb-[0.6rem]'>Voltar</Link>
                </div>
            </div>
        </div>
        <form onSubmit={(e)=>saveEvent(e)} className=" w-full flex flex-col gap-8 ">
          <div className=" flex flex-col">
            <p className=" font-bold text-font-input ml-3 mb-2 ">Nome</p>
            <label className=" flex items-center w-full mb-5">
              <FontAwesomeIcon icon={faTag} className=' absolute pl-3 text-font-icon text-sm' />
              <input onChange={(e)=>setName(e.target.value)} className=" bg-bg-white w-[350px] p-2 pl-10 rounded-md placeholder-font-placeholder text-font-input text-sm shadow-sm " type="text" name="name" id="name" placeholder="Nome" />
            </label>
            <p className=" font-bold text-font-input ml-3 mb-2 ">Tipo</p>
            <label className=" flex items-center w-full mb-5">
              <FontAwesomeIcon icon={faTag} className=' absolute pl-3 text-font-icon text-sm' />
              <select onChange={(e)=>setType(e.target.value)} className="bg-bg-white w-[350px] p-2 pl-10 rounded-md placeholder-font-placeholder text-font-input text-sm shadow-sm " name="eventType" id="eventType" >
                <option value="Festival">Festival</option>
                <option value="Workshop">Workshop</option>
                <option value="Show">Show</option>
                <option value="Talk">Talk</option>
              </select>
            </label>
            <p className=" font-bold text-font-input ml-3 mb-2 ">Data de início</p>
            <label className=" flex items-center w-full mb-5 last:mb-0 ">
              <FontAwesomeIcon icon={faCalendarDays} className=' absolute pl-3 text-font-icon text-sm' />
              <input onChange={(e)=>setInitialDate(e.target.value)} className=" bg-bg-white w-[350px] p-2 pl-10 rounded-md placeholder-font-placeholder text-font-placeholder text-sm shadow-sm " type="datetime-local" name="initialType" id="initialType" placeholder="Data de início" />
            </label>
            <p className=" font-bold text-font-input ml-3 mb-2 ">Data de finalização</p>
            <label className=" flex items-center w-full mb-5 last:mb-0 ">
              <FontAwesomeIcon icon={faCalendarDays} className=' absolute pl-3 text-font-icon text-sm' />
              <input onChange={(e)=>setFinalDate(e.target.value)} className=" bg-bg-white w-[350px] p-2 pl-10 rounded-md placeholder-font-placeholder text-font-placeholder text-sm shadow-sm " type="datetime-local" name="fianlDate" id="fianlDate" placeholder="Data de finalização" />
            </label>
            <p className=" font-bold text-font-input ml-3 mb-2 ">Lotação máxima</p>
            <label className=" flex items-center w-full mb-5">
              <FontAwesomeIcon icon={faUsers} className=' absolute pl-3 text-font-icon text-sm' />
              <input onChange={(e)=>setMaximumCapacity(Number(e.target.value))} className=" bg-bg-white w-[350px] p-2 pl-10 rounded-md placeholder-font-placeholder text-font-input text-sm shadow-sm " type="number" min={0} name="maximumCapacity" id="maximumCapacity" placeholder="Lotação máxima" />
            </label>
            <p className=" font-bold text-font-input ml-3 mb-2 ">Local</p>
            <label className=" flex items-center w-full mb-5">
              <FontAwesomeIcon icon={faLocationDot} className=' absolute pl-3 text-font-icon text-sm' />
              <input onChange={(e)=>setAddress(e.target.value)} className=" bg-bg-white w-[350px] p-2 pl-10 rounded-md placeholder-font-placeholder text-font-input text-sm shadow-sm " type="text" name="adress" id="adress" placeholder="Endereço" />
            </label>
            <p className=" font-bold text-font-input ml-3 mb-2 ">Descrição</p>
            <label className=" flex items-center w-full mb-5 last:mb-0 ">
              <FontAwesomeIcon icon={faComment} className=' absolute pl-3 text-font-icon text-sm' />
              <textarea onChange={(e)=>setDescription(e.target.value)} rows={4} className=" bg-bg-white w-[350px] p-2 pl-10 rounded-md placeholder-font-placeholder text-sm shadow-sm " name="description" id="description"  placeholder="Descrição" ></textarea>
            </label>
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
