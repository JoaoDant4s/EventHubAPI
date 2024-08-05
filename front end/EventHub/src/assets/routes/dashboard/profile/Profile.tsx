import { useNavigate } from "react-router-dom";
import Title from "../../../components/Title";
import Button from "../../../components/Button";
import { faArrowRight } from "@fortawesome/free-solid-svg-icons";
import { useEffect, useState } from "react";
import Alert, { getAlert, Status } from "../../../components/Alert";
import { apiGetAttractionById, apiGetByEmail } from "../../../api/services/user";
import { UserDTO, AttractionDTO } from "../../../api/services/user";
import { Role } from "../../../../main";

export default function Profile() {
  const navigate = useNavigate();
  const [user, setUser] = useState<UserDTO>();
  const [attraction, setAttraction] = useState<AttractionDTO>();
  const [role, setRole] = useState<Role>("participant");

  //ALERT STATES
  const [message, setMessage] = useState<string>("");
  const [status, setStatus] = useState<Status>("success");
  const [visible, setVisible] = useState<boolean>(false);
  const [title, setTitle] = useState<string>("Sucesso!");

  const setUserData = async () =>{
    const login = localStorage.getItem("login") || "";
    const response = await apiGetByEmail(login)
    .then((response)=>{
      const data = response?.data;
      setUser(data);
      return response
    });
  }
  
  const setAttractionData = async () =>{
    if(role=="attraction" && user != null){
      const attResponse = await apiGetAttractionById(user?.attractionId)
      .then((response)=>{
        const data = response?.data;
        setAttraction(data);
        localStorage.setItem("attraction",JSON.stringify(data));
      })
    }
  }

  useEffect(()=>{
    setUserData();
    getAlert(setMessage, setStatus, setVisible, setTitle);
    setRole(localStorage.getItem("role") || "participant");
  }, [])

  useEffect(()=>{
    setAttractionData();
  }, [user])

  return (
    <>
        <div className=" flex justify-between items-center mb-10">
            <Title>Perfil</Title>
            <Button type='submit' size="default" color='default' icon={faArrowRight} onClick={()=>navigate("/dashboard/profile/updateProfile")}>Editar perfil</Button>
        </div>
        <div className=" w-full p-4 pl-6 bg-gradient-to-r from-bg-white from-50% to-tertiary to-100% rounded-md shadow-md">
            <h3 className=" font-bold mb-4 text-font-title text-[1.4rem] ">Informações</h3>
            <p className=" text-font-text "><span className=" font-bold">Nome:</span> {user?.name}</p>
            <p className=" text-font-text "><span className=" font-bold">Email:</span> {user?.email}</p>
            <p className=" text-font-text "><span className=" font-bold">{role == "promoter"?"CNPJ:":"CPF:"}</span> {user?.cpf}</p>
            <p className=" text-font-text "><span className=" font-bold">{role == "promoter"?"Data de fundação:":"Data de nascimento:"}</span> {user?.birthDate}</p>
            <p className=" text-font-text "><span className=" font-bold">Idade:</span> {user?.age.toString()}</p>
            {
              role == "attraction"
              ?
              (
                <>
                  <p className=" text-font-text "><span className=" font-bold">Contato:</span> {attraction?.contact}</p>
                  <p className=" text-font-text "><span className=" font-bold">Descrição:</span> {attraction?.description}</p>
                </>
              )
              : null
            }
        </div>
        <Alert status={status} visible={visible} setVisible={setVisible} title={title.toString()}>
            <p className='text-justify text-xs pl-4 mt-2 text-font-text'>{message}</p>
        </Alert>
    </>
  )
}
