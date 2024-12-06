import { useNavigate } from "react-router-dom";
import Title from "../../../components/Title";
import Button from "../../../components/Button";
import { faArrowRight, faTrash } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { useEffect, useState } from "react";
import Alert, { getAlert, setAlert, Status } from "../../../components/Alert";
import { apiDeleteCreditCrad, apiGetByEmail, apiGetByParticipantId, CreditCardDTO, UserDTO } from "../../../api/services/user";
import { Role } from "../../../../main";

export default function Profile() {
  const navigate = useNavigate();
  
  const [user, setUser] = useState<UserDTO>();
  const [role, setRole] = useState<Role>("participant");
  const [creditCard, setCreditCard] = useState<CreditCardDTO>();

  //ALERT STATES
  const [message, setMessage] = useState<String>("");
  const [status, setStatus] = useState<Status>("success");
  const [visible, setVisible] = useState<boolean>(false);
  const [title, setTitle] = useState<String>("Sucesso!");

  
  const setUserData = async () =>{
    const login = localStorage.getItem("login") || "";
    const response = await apiGetByEmail(login)
    .then((response)=>{
      const data = response?.data;
      setUser(data);
      return response
    });
  }

  const setCreditCardInfo = async () => {
    if(user !== undefined){
      await apiGetByParticipantId(user.participantId)
        .then((response)=>{
          setCreditCard(response.data);
        })
    }
  }

const deleteCreditCard = async (id:Number)=>{
  await apiDeleteCreditCrad(id)
  .then((response)=>{
    setCreditCard(undefined);
  })
}

  useEffect(()=>{
    setUserData();
    getAlert(setMessage, setStatus, setVisible, setTitle);
    setRole(localStorage.getItem("role") || "participant");
  }, [])

  useEffect(()=>{
    setCreditCardInfo()
  }, [user])
  
  return (
    <>
        <div className=" flex justify-between items-center mb-10">
            <Title>Cartão de crédito</Title>
            {
              creditCard === undefined ?<Button type='submit' size="default" color='default' icon={faArrowRight} onClick={()=>navigate("/dashboard/creditCard/registerCreditCard")}>Cadastrar cartão</Button>:null
            }
        </div>
        {
          creditCard !== undefined
          ? (
            <div className=" w-[400px] p-6 h-[240px] bg-bg-white rounded-xl shadow-md flex flex-col justify-between ">
              <div className=" flex justify-between items-start ">
                <p className=" font-bold text-font-title text-[1.2rem] ">Nome do titular</p>
                <div onClick={()=>deleteCreditCard(creditCard.id)} className=" bg-danger-400 w-8 h-8 p-2 flex justify-center items-center rounded-md cursor-pointer ">
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
          )
          : (
            <div className=" w-full flex mb-5 justify-center items-center p-8 bg-bg-white rounded-md shadow-md">
                <h3 className=" font-bold text-font-text text-[1.4rem] ">Nenhum cartão de crédito cadastrado</h3>
            </div>
          )
        }
        <Alert status={status} visible={visible} setVisible={setVisible} title={title.toString()}>
            <p className='text-justify text-xs pl-4 mt-2 text-font-text'>{message}</p>
        </Alert>
    </>
  )
}
