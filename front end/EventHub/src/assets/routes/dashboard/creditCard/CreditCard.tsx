import { Link, useNavigate } from "react-router-dom";
import Title from "../../../components/Title";
import Button from "../../../components/Button";
import { faArrowRight, faChevronRight, faTrash } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

export default function Profile() {
  const navigate = useNavigate();

  return (
    <>
        <div className=" flex justify-between items-center mb-10">
            <Title>Cartão de crédito</Title>
            <Button type='submit' size="default" color='default' icon={faArrowRight} onClick={()=>navigate("/dashboard/creditCard/registerCreditCard")}>Cadastrar cartão</Button>
        </div>
        <div className=" w-full flex mb-5 justify-center items-center p-8 bg-bg-white rounded-md shadow-md">
            <h3 className=" font-bold text-font-text text-[1.4rem] ">Nenhum cartão de crédito cadastrado</h3>
        </div>
        <div className=" w-[400px] p-6 h-[240px] bg-bg-white rounded-xl shadow-md flex flex-col justify-between ">
          <div className=" flex justify-between items-start ">
            <p className=" font-bold text-font-title text-[1.2rem] ">Nome do titular</p>
            <div className=" bg-danger-400 w-8 h-8 p-2 flex justify-center items-center rounded-md cursor-pointer ">
              <FontAwesomeIcon icon={faTrash} className=' text-font-white text-[1rem]' />
            </div>
          </div>
          <div>
            <div>
              <p className=" text-font-text font-bold text-[0.8rem] ">Validade</p>
              <p className=" text-font-title font-bold text-[1.2rem] ">12/30</p>
            </div>
            <div>
              <p className=" text-font-text font-bold text-[0.8rem] ">Número</p>
              <p className=" text-font-title font-bold text-[1.2rem] ">0000-0000-0000-0000</p>
            </div>
          </div>
        </div>
    </>
  )
}
