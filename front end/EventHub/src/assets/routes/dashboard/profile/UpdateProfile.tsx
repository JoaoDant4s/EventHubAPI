import { Link, useNavigate } from "react-router-dom";
import Title from "../../../components/Title";
import Button from "../../../components/Button";
import { faAddressCard, faArrowRight, faCalendarDays, faChevronRight, faEnvelope, faUser } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { FormEvent, SetStateAction, useState } from "react";
import InputMask from 'react-input-mask';

export default function Profile() {
  const navigate = useNavigate();

  const [name, setName] = useState<String>("");
  const [email, setEmail] = useState<String>("");
  const [cpf, setCpf] = useState<String>("");
  const [birthDate, setBirthDate] = useState<String>("");

  const updateUser=(e: FormEvent<HTMLFormElement>)=> {
    throw new Error("Function not implemented.");
  }

  return (
    <>
        <div className=" flex justify-between items-center mb-10">
            <div className='flex items-end'>
                <Title>Editar Perfil</Title>
                <div className=" flex items-end ">
                  <FontAwesomeIcon icon={faChevronRight} className=' pl-3 text-font-text text-[0.6rem] mb-4 ' />
                  <Link to="/dashboard/profile" className=' pl-3 text-font-text text-[0.8rem] mb-[0.7rem]'>Voltar</Link>
                </div>
            </div>
        </div>
        <form onSubmit={(e)=>updateUser(e)} className=" w-full flex flex-col gap-8 ">
          <div className=" flex flex-col">
            <p className=" font-bold text-font-input ml-3 mb-2 ">Nome</p>
            <label className=" flex items-center w-full mb-5">
              <FontAwesomeIcon icon={faUser} className=' absolute pl-3 text-font-icon text-sm' />
              <input onChange={(e)=>setName(e.target.value)} className=" bg-bg-white w-[350px] p-2 pl-10 rounded-md placeholder-font-placeholder text-font-input text-sm shadow-sm " type="text" name="name" id="name" placeholder="Nome" />
            </label>
            <p className=" font-bold text-font-input ml-3 mb-2 ">E-mail</p>
            <label className=" flex items-center w-full mb-5">
              <FontAwesomeIcon icon={faEnvelope} className=' absolute pl-3 text-font-icon text-sm' />
              <input onChange={(e)=>setEmail(e.target.value)} className=" bg-bg-white w-[350px] p-2 pl-10 rounded-md placeholder-font-placeholder text-font-input text-sm shadow-sm " type="email" name="email" id="email" placeholder="E-mail" />
            </label>
            <p className=" font-bold text-font-input ml-3 mb-2 ">CPF</p>
            <label className=" flex items-center w-full mb-5 ">
              <FontAwesomeIcon icon={faAddressCard} className=' absolute pl-3 text-font-icon text-sm' />
              <InputMask mask="999.999.999-99" onChange={(e: { target: { value: SetStateAction<String>; }; })=>setCpf(e.target.value)} className=" bg-bg-white w-[350px] p-2 pl-10 rounded-md placeholder-font-placeholder text-font-input text-sm shadow-sm " type="text" name="cpf" id="cpf" placeholder="CPF" />
            </label>
            <p className=" font-bold text-font-input ml-3 mb-2 ">Data de nascimento</p>
            <label className=" flex items-center w-full mb-5 last:mb-0 ">
              <FontAwesomeIcon icon={faCalendarDays} className=' absolute pl-3 text-font-icon text-sm' />
              <input onChange={(e)=>setBirthDate(e.target.value)} className=" bg-bg-white w-[350px] p-2 pl-10 rounded-md placeholder-font-placeholder text-font-placeholder text-sm shadow-sm " type="date" name="birthDate" id="birthDate" placeholder="Data de nascimento" />
            </label>
          </div>
          <div className=" grid grid-cols-2 w-full gap-4">
            <Button size="default" color='default' icon={faArrowRight} >Salvar</Button>
          </div>
        </form>
    </>
  )
}
