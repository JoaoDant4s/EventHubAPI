
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { useNavigate } from "react-router-dom"
import { faEnvelope } from '@fortawesome/free-solid-svg-icons';
import { faLock } from '@fortawesome/free-solid-svg-icons';
import { faArrowRight } from '@fortawesome/free-solid-svg-icons';
import Button from '../../components/Button';

export default function Login() {

  const navigate = useNavigate();

  const login=()=>{
    localStorage.setItem("token", "token");
    navigate("/dashboard");
  }
  


  return (
    <section className=" bg-bg-index w-full h-[100vh] flex justify-center items-center ">
      <main className=" bg-bg-white rounded-xl shadow-lg w-96 p-10 ">
        <form className=" w-full flex flex-col gap-8 ">
          <h1 className=" font-bold text-[2rem] text-font-title ">Login</h1>
          <div className=" flex flex-col w-full gap-4">
            <label className=" flex items-center w-full">
              <FontAwesomeIcon icon={faEnvelope} className=' absolute pl-3 text-font-icon text-sm' />
              <input className=" bg-bg-input w-full p-2 pl-10  w-full rounded-md placeholder-font-placeholder text-sm " type="email" name="email" id="email" placeholder="E-mail" />
            </label>
            <label className=" flex items-center w-full ">
              <FontAwesomeIcon icon={faLock} className=' absolute pl-3 text-font-icon text-sm' />
              <input className=" bg-bg-input w-full p-2 pl-10  w-full rounded-md placeholder-font-placeholder text-sm " type="password" name="password" id="password" placeholder="Senha" />
            </label>
          </div>
          <div className=" flex flex-col w-full gap-4">
            <Button color='default' icon={faArrowRight} onClick={()=>login()}>Entrar</Button>
            <Button color="transparency" onClick={()=>navigate("/register")}>Não possuo uma conta</Button>
          </div>
        </form>
      </main>
    </section>
  )
}
