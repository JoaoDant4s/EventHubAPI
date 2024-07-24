import { Link, useNavigate } from "react-router-dom";
import Title from "../../../components/Title";
import Button from "../../../components/Button";
import { faArrowRight, faChevronRight } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

export default function Profile() {
  const navigate = useNavigate();

  return (
    <>
        <div className=" flex justify-between items-center mb-10">
            <Title>Perfil</Title>
            <Button type='submit' size="default" color='default' icon={faArrowRight} onClick={()=>navigate("/dashboard/profile/updateProfile")}>Editar perfil</Button>
        </div>
        <div className=" w-full p-4 pl-6 bg-gradient-to-r from-bg-white from-50% to-tertiary to-100% rounded-md shadow-sm">
            <h3 className=" font-bold mb-4 text-font-title text-[1.4rem] ">Informações</h3>
            <p className=" text-font-text "><span className=" font-bold">Nome:</span> Nome</p>
            <p className=" text-font-text "><span className=" font-bold">Email:</span> Email</p>
            <p className=" text-font-text "><span className=" font-bold">CPF:</span> CPF</p>
            <p className=" text-font-text "><span className=" font-bold">Idade:</span> Idade</p>
        </div>
    </>
  )
}
