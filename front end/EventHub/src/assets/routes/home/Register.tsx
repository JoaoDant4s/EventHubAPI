
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { Link, useNavigate } from "react-router-dom"
import { faUser, faEnvelope, faAddressCard, faCalendarDays, faLock, faArrowRight, faChevronRight, faCircleExclamation } from '@fortawesome/free-solid-svg-icons';

import Button from '../../components/Button';
import { FormEvent, SetStateAction, useState } from 'react';
import { apiParticipantRegistration, participantRegistrationDTO } from '../../api/services/user';
import Alert, { getAlert, setAlert, Status } from '../../components/Alert';
import { PatternFormat } from 'react-number-format';

export default function Register() {
  const navigate = useNavigate();


  const [name, setName] = useState<String>("");
  const [email, setEmail] = useState<String>("");
  const [cpf, setCpf] = useState<String>("");
  const [birthDate, setBirthDate] = useState<String>("");
  const [password, setPassword] = useState<String>("");
  const [confirmPassword, setConfirmPassword] = useState<String>("");

  //ALERT STATES
  const [message, setMessage] = useState<String>("");
  const [status, setStatus] = useState<Status>("success");
  const [visible, setVisible] = useState<boolean>(false);
  const [title, setTitle] = useState<String>("Sucesso!");

  const register = async (e:FormEvent)=>{
    e.preventDefault();
    
    const participant:participantRegistrationDTO = {
      email: email,
      password: password,
      confirmPassword: confirmPassword,
      name: name,
      cpf: cpf,
      birthDate: birthDate,
    }

    const response = await apiParticipantRegistration(participant)
    .then((response)=>{
      const data = response?.data;
      console.log(data)
      
      setAlert("Participante cadastrado com sucesso!", "success", true);
      navigate("/");
    })
    .catch((e)=>{
      console.log(e.response.data);
      if(e.response.data.detail == null){
        setAlert("Algo inesperado aconteceu", "alert", true);
      } else {
        setAlert(e.response.data.detail, "success", true);
      }
      getAlert(setMessage, setStatus, setVisible, setTitle);

    });
  }

  return (
    <section className=" bg-bg-index w-full h-[100vh] flex justify-center items-center ">
      <main className=" bg-bg-white rounded-xl shadow-lg w-[48rem] p-10 ">
        <form onSubmit={(e)=>register(e)} className=" w-full flex flex-col gap-8 ">
          <div className='flex items-end'>
            <h1 className=" font-bold text-[2rem] text-font-title ">Cadastro</h1>
            <FontAwesomeIcon icon={faChevronRight} className=' pl-3 text-font-text text-[0.6rem] mb-3 ' />
            <Link to="/" className=' pl-3 text-font-text text-[0.8rem]  mb-[0.4rem]'>Login</Link>
          </div>
          <div className=" grid grid-cols-2 w-full gap-4">
            <label className=" flex items-center w-full">
              <FontAwesomeIcon icon={faUser} className=' absolute pl-3 text-font-icon text-sm' />
              <input onChange={(e)=>setName(e.target.value)} className=" bg-bg-input w-full p-2 pl-10 rounded-md placeholder-font-placeholder text-font-input text-sm " type="text" name="name" id="name" placeholder="Nome" />
            </label>
            <label className=" flex items-center w-full">
              <FontAwesomeIcon icon={faEnvelope} className=' absolute pl-3 text-font-icon text-sm' />
              <input onChange={(e)=>setEmail(e.target.value)} className=" bg-bg-input w-full p-2 pl-10 rounded-md placeholder-font-placeholder text-font-input text-sm " type="email" name="email" id="email" placeholder="E-mail" />
            </label>
            <label className=" flex items-center w-full ">
              <FontAwesomeIcon icon={faAddressCard} className=' absolute pl-3 text-font-icon text-sm' />
              <PatternFormat format="###.###.###-##" mask={"_"} allowEmptyFormatting onChange={(e: { target: { value: SetStateAction<String>; }; })=>setCpf(e.target.value)} className=" bg-bg-input w-full p-2 pl-10 rounded-md placeholder-font-placeholder text-font-input text-sm " type="text" name="cpf" id="cpf" placeholder="CPF" />
            </label>
            <label className=" flex items-center w-full ">
              <FontAwesomeIcon icon={faCalendarDays} className=' absolute pl-3 text-font-icon text-sm' />
              <input onChange={(e)=>setBirthDate(e.target.value)} className=" bg-bg-input w-full p-2 pl-10 rounded-md placeholder-font-placeholder text-font-placeholder text-sm " type="date" name="birthDate" id="birthDate" placeholder="Data de nascimento" />
            </label>
            <label className=" flex items-center w-full ">
              <FontAwesomeIcon icon={faLock} className=' absolute pl-3 text-font-icon text-sm' />
              <input onChange={(e)=>setPassword(e.target.value)} className=" bg-bg-input w-full p-2 pl-10 rounded-md placeholder-font-placeholder text-font-input text-sm " type="password" name="password" id="password" placeholder="Senha" />
            </label>
            <label className=" flex items-center w-full ">
              <FontAwesomeIcon icon={faLock} className=' absolute pl-3 text-font-icon text-sm' />
              <input onChange={(e)=>setConfirmPassword(e.target.value)} className=" bg-bg-input w-full p-2 pl-10 rounded-md placeholder-font-placeholder text-font-input text-sm " type="password" name="confirmPassword" id="confirmPassword" placeholder="Confirmação de senha" />
            </label>
          </div>
          <div className=" grid grid-cols-2 w-full gap-4">
            <Button color='default' icon={faArrowRight} >Entrar</Button>
            <div></div>
            <Button color="transparency" onClick={()=>navigate("/")}>Já possuo conta, logar</Button>
          </div>
        </form>
      </main>
        <Alert status={status} visible={visible} setVisible={setVisible} title={title.toString()}>
            <p className='text-justify text-xs pl-4 mt-2 text-font-text'>{message}</p>
        </Alert>
    </section>
  )
}
