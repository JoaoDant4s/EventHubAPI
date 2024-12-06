import { Link, useNavigate, useParams } from "react-router-dom";
import Title from "../../../components/Title";
import Button from "../../../components/Button";
import { faArrowRight, faChevronRight } from "@fortawesome/free-solid-svg-icons";
import { useEffect, useState } from "react";
import Alert, { getAlert, Status } from "../../../components/Alert";
import { apiGetAttractionById, apiGetByEmail } from "../../../api/services/user";
import { UserDTO, AttractionDTO } from "../../../api/services/user";
import { Role } from "../../../../main";
import { apiGetEventById, EventDTO } from "../../../api/services/events";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import bgEvent from "../../../imgs/bgEvents.png"

export default function EventProfile() {
  const navigate = useNavigate();
  const { id } = useParams();
  const [event,setEvent] = useState<EventDTO>();

  //ALERT STATES
  const [message, setMessage] = useState<String>("");
  const [status, setStatus] = useState<Status>("success");
  const [visible, setVisible] = useState<boolean>(false);
  const [title, setTitle] = useState<String>("Sucesso!");

  const getEvent=async()=>{
    await apiGetEventById(Number(id))
      .then((response)=>{
        setEvent(response.data)
      });
  }

  useEffect(()=>{
    getEvent()
  }, [id])

  
  return (
    <>
        <div className=" flex justify-between items-center mb-10">
            <div className='flex items-end'>
                <Title>{event?.name}</Title>
                <div className=" flex items-end ">
                  <FontAwesomeIcon icon={faChevronRight} className=' pl-3 text-font-text text-[0.6rem] mb-4 ' />
                  <Link to="/dashboard" className=' pl-3 text-font-text text-[0.8rem] mb-[0.6rem]'>Voltar</Link>
                </div>
            </div>
            <Button type='submit' size="default" color='default' icon={faArrowRight} onClick={()=>navigate("/dashboard")}>Comprar ingresso</Button>
        </div>
          <div onClick={()=>navigate(`/dashboard/event/${event?.id}`)} className=" h-[200px] my-5 bg-bg-white rounded-md shadow-md relative z-0 ">
            <div className=" mix-blend-normal z-10">
              <img className="absolute h-[200px] w-1/2 object-cover object-right right-0 rounded-md " src={bgEvent} alt="banner do evento"  />
              <div className=" absolute z-10 bg-gradient-to-r from-bg-white w- h-[200px] w-1/2 via-transparency right-0" />
            </div>
            <div className=" h-full flex flex-col justify-center p-4 pl-20 absolute z-10 ">
                <h3 className="text-[2rem] font-bold text-font-title">{event?.name}</h3>
            </div>
          </div>
        <Alert status={status} visible={visible} setVisible={setVisible} title={title.toString()}>
            <p className='text-justify text-xs pl-4 mt-2 text-font-text'>{message}</p>
        </Alert>
    </>
  )
}
