
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { useNavigate } from "react-router-dom"
import { faEnvelope } from '@fortawesome/free-solid-svg-icons';
import { faLock } from '@fortawesome/free-solid-svg-icons';
import { faArrowRight } from '@fortawesome/free-solid-svg-icons';
import { faCircleExclamation } from '@fortawesome/free-solid-svg-icons';
import Button from '../../components/Button';
import Alert, { getAlert, setAlert } from '../../components/Alert';
import { Status } from '../../components/Alert';
import { ComponentProps, FormEvent, useEffect, useState } from 'react';
import { apiLogin, LoginDTO } from '../../api/services/user';
import { NavItem } from '../../../main';


export default function Login() {
  const navigate = useNavigate();

  const [email, setEmail] = useState<String>("");
  const [password, setPassword] = useState<String>("");

  //ALERT STATES
  const [message, setMessage] = useState<String>("");
  const [status, setStatus] = useState<Status>("success");
  const [visible, setVisible] = useState<boolean>(false);
  const [title, setTitle] = useState<String>("Sucesso!");


  interface authResponse {
    login:String,
    roles:Array<String>
    token:String
  }

  const login = async (e:FormEvent)=>{
    e.preventDefault();
    
    const auth:LoginDTO = {
      email: email,
      password: password
    }

    const response = await apiLogin(auth)
    .then((response)=>{
      const data:authResponse = response?.data;
      localStorage.setItem("login", data.login.toString());
      localStorage.setItem("token", data.token.toString());
      
      if(data.roles.includes("ROLE_ADMIN")){
        localStorage.setItem("role", "admin");
      } else if(data.roles.includes("ROLE_PROMOTER")){
        localStorage.setItem("role", "promoter");
      } else if(data.roles.includes("ROLE_ATTRACTION")){
        localStorage.setItem("role", "attraction");
      } else {
        localStorage.setItem("role", "participant");
      }

      navigate("/dashboard");
    })
    .catch((e)=>{
      console.log(e.response.data);
      setAlert(e.response.data.message, "alert", true);
      getAlert(setMessage, setStatus, setVisible, setTitle);
    });
  }
  

  const setUserRole=(role:String)=>{
    if(role == "ROLE_ADMIN") {
      return "admin";
    } else if(role == "ROLE_PROMOTER") {
      return "promoter";
    } else if(role == "ROLE_PROMOTER") {
      return "attraction";
    } else {
      return "participant"
    }
  }

  useEffect(()=>{
    getAlert(setMessage, setStatus, setVisible, setTitle);
  })

  return (
    <section className=" bg-bg-index w-full h-[100vh] flex justify-center items-center ">
      <main className=" bg-bg-white rounded-xl shadow-lg w-96 p-10 ">
        <form onSubmit={(e)=>login(e)} className=" w-full flex flex-col gap-8 ">
          <h1 className=" font-bold text-[2rem] text-font-title ">Login</h1>
          <div className=" flex flex-col w-full gap-4">
            <label className=" flex items-center w-full">
              <FontAwesomeIcon icon={faEnvelope} className=' absolute pl-3 text-font-icon text-sm' />
              <input onChange={(e)=>setEmail(e.target.value)} className=" bg-bg-input w-full p-2 pl-10  w-full rounded-md placeholder-font-placeholder text-sm " type="text" name="email" id="email" placeholder="E-mail" />
            </label>
            <label className=" flex items-center w-full ">
              <FontAwesomeIcon icon={faLock} className=' absolute pl-3 text-font-icon text-sm' />
              <input onChange={(e)=>setPassword(e.target.value)} className=" bg-bg-input w-full p-2 pl-10  w-full rounded-md placeholder-font-placeholder text-sm " type="password" name="password" id="password" placeholder="Senha" />
            </label>
          </div>
          <div className=" flex flex-col w-full gap-4">
            <Button type='submit' color='default' icon={faArrowRight}>Entrar</Button>
            <Button color="transparency" onClick={()=>navigate("/register")}>Não possuo uma conta</Button>
          </div>
        </form>
        <Alert status={status} visible={visible} setVisible={setVisible} title={title.toString()}>
            <p className='text-justify text-xs pl-4 mt-2 text-font-text'>{message}</p>
        </Alert>
      </main>
    </section>
  )
}
