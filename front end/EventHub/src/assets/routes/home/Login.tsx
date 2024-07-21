
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { useNavigate } from "react-router-dom"
import { faEnvelope } from '@fortawesome/free-solid-svg-icons';
import { faLock } from '@fortawesome/free-solid-svg-icons';
import { faArrowRight } from '@fortawesome/free-solid-svg-icons';

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
            <div className=" flex items-center w-full">
              <FontAwesomeIcon icon={faEnvelope} className=' absolute pl-3 text-font-icon text-sm' />
              <input className=" bg-bg-input w-full p-2 pl-10  w-full rounded-md placeholder-font-placeholder text-sm " type="email" name="email" id="email" placeholder="E-mail" />
            </div>
            <div className=" flex items-center w-full ">
              <FontAwesomeIcon icon={faLock} className=' absolute pl-3 text-font-icon text-sm' />
              <input className=" bg-bg-input w-full p-2 pl-10  w-full rounded-md placeholder-font-placeholder text-sm " type="password" name="password" id="password" placeholder="Senha" />
            </div>
          </div>
          <div className=" flex flex-col w-full gap-4">
            <button onClick={()=>login()} className=' bg-gradient-to-r from-primary to-secondary p-2 px-4 flex items-center justify-between text-bg-white font-bold rounded-md'>
              Entrar
              <FontAwesomeIcon icon={faArrowRight} className=' text-white ' />
            </button>
            <button onClick={()=>login()} className=' p-2 px-4 flex justify-between text-primary font-bold rounded-md'>
              Não possuo uma conta
            </button>
          </div>
        </form>
      </main>
    </section>
  )
}
