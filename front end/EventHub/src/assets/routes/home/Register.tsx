
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { Link, useNavigate } from "react-router-dom"
import { faUser, faEnvelope, faAddressCard, faCalendarDays, faLock, faArrowRight, faChevronRight } from '@fortawesome/free-solid-svg-icons';

import Button from '../../components/Button';

export default function Register() {

  const navigate = useNavigate();

  const register=()=>{
    localStorage.setItem("token", "token");
    navigate("/dashboard");
  }


  return (
    <section className=" bg-bg-index w-full h-[100vh] flex justify-center items-center ">
      <main className=" bg-bg-white rounded-xl shadow-lg w-[48rem] p-10 ">
        <form className=" w-full flex flex-col gap-8 ">
          <div className='flex items-end'>
            <h1 className=" font-bold text-[2rem] text-font-title ">Cadastro</h1>
            <FontAwesomeIcon icon={faChevronRight} className=' pl-3 text-font-text text-[0.6rem] mb-3 ' />
            <Link to="/" className=' pl-3 text-font-text text-[0.8rem]  mb-[0.4rem]'>Login</Link>
          </div>
          <div className=" grid grid-cols-2 w-full gap-4">
            <label className=" flex items-center w-full">
              <FontAwesomeIcon icon={faUser} className=' absolute pl-3 text-font-icon text-sm' />
              <input className=" bg-bg-input w-full p-2 pl-10  w-full rounded-md placeholder-font-placeholder text-font-input text-sm " type="text" name="name" id="name" placeholder="Nome" />
            </label>
            <label className=" flex items-center w-full">
              <FontAwesomeIcon icon={faEnvelope} className=' absolute pl-3 text-font-icon text-sm' />
              <input className=" bg-bg-input w-full p-2 pl-10  w-full rounded-md placeholder-font-placeholder text-font-input text-sm " type="email" name="email" id="email" placeholder="E-mail" />
            </label>
            <label className=" flex items-center w-full ">
              <FontAwesomeIcon icon={faAddressCard} className=' absolute pl-3 text-font-icon text-sm' />
              <input className=" bg-bg-input w-full p-2 pl-10  w-full rounded-md placeholder-font-placeholder text-font-input text-sm " type="text" name="cpf" id="cpf" placeholder="CPF" />
            </label>
            <label className=" flex items-center w-full ">
              <FontAwesomeIcon icon={faCalendarDays} className=' absolute pl-3 text-font-icon text-sm' />
              <input className=" bg-bg-input w-full p-2 pl-10  w-full rounded-md placeholder-font-placeholder text-font-placeholder text-sm " type="date" name="birthDate" id="birthDate" placeholder="Data de nascimento" />
            </label>
            <label className=" flex items-center w-full ">
              <FontAwesomeIcon icon={faLock} className=' absolute pl-3 text-font-icon text-sm' />
              <input className=" bg-bg-input w-full p-2 pl-10  w-full rounded-md placeholder-font-placeholder text-font-input text-sm " type="password" name="password" id="password" placeholder="Senha" />
            </label>
            <label className=" flex items-center w-full ">
              <FontAwesomeIcon icon={faLock} className=' absolute pl-3 text-font-icon text-sm' />
              <input className=" bg-bg-input w-full p-2 pl-10  w-full rounded-md placeholder-font-placeholder text-font-input text-sm " type="password" name="password" id="password" placeholder="Confirmação de senha" />
            </label>
          </div>
          <div className=" grid grid-cols-2 w-full gap-4">
            <Button color='default' icon={faArrowRight} onClick={()=>register()}>Entrar</Button>
            <div></div>
            <Button color="transparency" onClick={()=>navigate("/")}>Já possuo conta, logar</Button>
          </div>
        </form>
      </main>
    </section>
  )
}
