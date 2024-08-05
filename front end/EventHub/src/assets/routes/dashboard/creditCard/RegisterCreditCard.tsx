import { Link, useNavigate } from "react-router-dom";
import Title from "../../../components/Title";
import Button from "../../../components/Button";
import { faArrowRight, faCreditCard, faChevronRight, faUser, faCalendarDays } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { FormEvent, SetStateAction, useEffect, useState } from "react";
import { PatternFormat } from "react-number-format";
import { apiGetByEmail, apiSaveCreditCard, SaveCreditCardDTO, UserDTO } from "../../../api/services/user";
import Alert, { getAlert, setAlert, Status } from "../../../components/Alert";
import { Role } from "../../../../main";

export default function RegisterCreditCard() {
  const navigate = useNavigate();

  const [name, setName] = useState<string>("");
  const [cardNumber, setCardNumber] = useState<string>("");
  const [expiration, setExpiration] = useState<string>("");
  
  const [user, setUser] = useState<UserDTO>();
  const [role, setRole] = useState<Role>("participant");

  //ALERT STATES
  const [message, setMessage] = useState<string>("");
  const [status, setStatus] = useState<Status>("success");
  const [visible, setVisible] = useState<boolean>(false);
  const [title, setTitle] = useState<string>("Sucesso!");
  
  const registerCreditCard = async (e:FormEvent<HTMLFormElement>)=>{
    e.preventDefault();
    
    const saveCreditCardDTO:SaveCreditCardDTO = {
      participantId: Number(user?.participantId),
      cardNumber: cardNumber,
      expiration: expiration,
      cardHolderName: name
    }

    const response = await apiSaveCreditCard(saveCreditCardDTO)
    .then((response)=>{
      const data = response?.data;
      setAlert("Informações atualizadas com sucesso!", "success", true);
      navigate("/dashboard/creditCard");
    })
    .catch((e)=>{
      console.log(e);
      if(e.response.data.detail == null){
        setAlert("Algo inesperado aconteceu", "alert", true);
      } else {
        setAlert(e.response.data.detail, "alert", true);
      }
      getAlert(setMessage, setStatus, setVisible, setTitle);

    });
  }
  
  const setUserData = async () =>{
    const login = localStorage.getItem("login") || "";
    const response = await apiGetByEmail(login)
    .then((response)=>{
      const data = response?.data;
      setUser(data);
      return response
    });
  }

  useEffect(()=>{
    setUserData();
    getAlert(setMessage, setStatus, setVisible, setTitle);
    setRole(localStorage.getItem("role") || "participant");
  }, [])

  return (
    <>
        <div className=" flex justify-between items-center mb-10">
            <div className='flex items-end'>
            <Title>Cadastrar cartão</Title>
                <div className=" flex items-end ">
                  <FontAwesomeIcon icon={faChevronRight} className=' pl-3 text-font-text text-[0.6rem] mb-4 ' />
                  <Link to="/dashboard/creditCard" className=' pl-3 text-font-text text-[0.8rem] mb-[0.6rem]'>Voltar</Link>
                </div>
            </div>
        </div>
        <form onSubmit={(e)=>registerCreditCard(e)} className=" w-full flex flex-col gap-8 ">
          <div className=" flex flex-col">
            <p className=" font-bold text-font-input ml-3 mb-2 ">Nome do titular</p>
            <label className=" flex items-center w-full mb-5">
              <FontAwesomeIcon icon={faUser} className=' absolute pl-3 text-font-icon text-sm' />
              <input onChange={(e)=>setName(e.target.value)} className=" bg-bg-white w-[350px] p-2 pl-10 rounded-md placeholder-font-placeholder text-font-input text-sm shadow-sm " type="text" name="name" id="name" placeholder="Nome do titular" />
            </label>
            <p className=" font-bold text-font-input ml-3 mb-2 ">Data de vencimento</p>
            <label className=" flex items-center w-full mb-5">
              <FontAwesomeIcon icon={faCalendarDays} className=' absolute pl-3 text-font-icon text-sm' />
              <input onChange={(e)=>setExpiration(e.target.value)} className=" bg-bg-white w-[350px] p-2 pl-10 rounded-md placeholder-font-placeholder text-font-input text-sm shadow-sm " type="date" name="expiration" id="expiration" placeholder="Data de vencimento" />
            </label>
            <p className=" font-bold text-font-input ml-3 mb-2 ">Número do cartão</p>
            <label className=" flex items-center w-full mb-5 ">
              <FontAwesomeIcon icon={faCreditCard} className=' absolute pl-3 text-font-icon text-sm' />
              <PatternFormat format="#### #### #### ####" mask={"_"} onChange={(e: { target: { value: SetStateAction<string>; }; })=>setCardNumber(e.target.value)} className=" bg-bg-white w-[350px] p-2 pl-10 rounded-md placeholder-font-placeholder text-font-input text-sm shadow-sm " type="text" name="cardNumber" id="Número do cartão" placeholder="cardNumber" />
            </label>
          </div>
          <div className=" grid grid-cols-2 w-full gap-4">
            <Button size="default" color='default' icon={faArrowRight} >Salvar</Button>
          </div>
        </form>
        <Alert status={status} visible={visible} setVisible={setVisible} title={title.toString()}>
            <p className='text-justify text-xs pl-4 mt-2 text-font-text'>{message}</p>
        </Alert>
    </>
  )
}
