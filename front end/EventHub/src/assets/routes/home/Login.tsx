import { useNavigate } from "react-router-dom"

export default function Login() {

  const navigate = useNavigate();

  const login=()=>{
    localStorage.setItem("token", "token");
    navigate("/dashboard");
  }

  return (
    
    <h1 className="font-bold underline">
      Hello world!
      <br/><br/>
      <button onClick={()=>login()}>
        Entrar
      </button>



    </h1>
  )
}
