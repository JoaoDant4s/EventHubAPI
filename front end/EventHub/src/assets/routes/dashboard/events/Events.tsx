import { useNavigate } from "react-router-dom";
import Title from "../../../components/Title";
import bgEvent from "../../../imgs/bgEvents.png"
import Button from "../../../components/Button";
import { faArrowRight } from "@fortawesome/free-solid-svg-icons";
import { useEffect, useState } from "react";
import { apiGetEventList, EventDTO } from "../../../api/services/events";
import { formatDate } from "../../../api/services/utils";

export default function events() {
  const navigate = useNavigate();
  const [events, setEvents] = useState<Array<EventDTO>>();
  
  const getEvents=async()=>{
    if(events === undefined){
      await apiGetEventList()
      .then((response)=>{
        setEvents(response.data);
      });
    }
  }
  
  useEffect(()=>{
    getEvents()
  })

  return (
    <>
        <div className=" flex gap-8 mb-10">
          <div className="bg-bg-white h-32 flex justify-center items-center flex-col flex-grow rounded-md shadow-md border-b-[14px] border-primary ">
            <h2 className=" font-bold text-[1.2rem] text-font-subtitle ">Eventos</h2>
            <p className=" font-bold text-[1.2rem] text-font-placeholder ">{events?.length}</p>
          </div>
        </div>
        <div className=" flex justify-between items-center mb-10">
        <Title>Proximos Eventos</Title>
            <Button type='submit' size="default" color='default' icon={faArrowRight} onClick={()=>navigate("/admin/events/eventRegistration")}>Cadastrar</Button>
        </div>
        {events && events.map((event,index)=>(
          <div key={`event-${index}`} onClick={()=>navigate(`/dashboard/event/${event.id}`)} className=" h-[180px] my-5 cursor-pointer bg-bg-white rounded-md shadow-md relative z-0 ">
            <div className=" mix-blend-normal z-10">
              <img className="absolute h-[180px] w-1/2 object-cover object-right right-0 rounded-md " src={bgEvent} alt="banner do evento"  />
              <div className=" absolute z-10 bg-gradient-to-r from-bg-white h-[180px] w-1/2 via-transparency right-0" />
            </div>
            <div className=" h-full flex flex-col justify-between p-4 pl-6 absolute z-10 ">
              <div>
                <h3 className="text-[1.4rem] font-bold text-font-subtitle">{event.name}</h3>
                <p className=" text-font-subtitle ">{formatDate(event.initialDate)}</p>
              </div>
              <Button size="default" color='default' icon={faArrowRight}>Visualizar</Button>
            </div>
          </div>
        ))}
    </>
  )
}
