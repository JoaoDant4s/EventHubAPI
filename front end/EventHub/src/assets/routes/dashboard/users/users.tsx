import { useNavigate } from "react-router-dom";
import Title from "../../../components/Title";
import Button from "../../../components/Button";
import { faArrowRight, faTrash, faUser, faPenToSquare } from "@fortawesome/free-solid-svg-icons";
import { useEffect, useState } from "react";
import Alert, { getAlert, Status } from "../../../components/Alert";
import { apiGetAttractionById, apiGetByEmail } from "../../../api/services/user";
import { UserDTO, AttractionDTO } from "../../../api/services/user";
import { Role } from "../../../../main";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

export default function Users() {
  const navigate = useNavigate();
  const [user, setUser] = useState<UserDTO>();
  const [attraction, setAttraction] = useState<AttractionDTO>();
  const [role, setRole] = useState<Role>("participant");

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
        <div className=" flex gap-8 mb-10">
          <div className="bg-bg-white h-32 flex justify-center items-center flex-col flex-grow rounded-md shadow-md border-b-[14px] border-primary ">
            <h2 className=" font-bold text-[1.2rem] text-font-subtitle ">Participantes</h2>
            <p className=" font-bold text-[1.2rem] text-font-placeholder ">100</p>
          </div>
          <div className="bg-bg-white h-32 flex justify-center items-center flex-col flex-grow rounded-md shadow-md border-b-[14px] border-secondary ">
            <h2 className=" font-bold text-[1.2rem] text-font-subtitle ">Atrações</h2>
            <p className=" font-bold text-[1.2rem] text-font-placeholder ">20</p>
          </div>
          <div className="bg-bg-white h-32 flex justify-center items-center flex-col flex-grow rounded-md shadow-md border-b-[14px] border-tertiary ">
            <h2 className=" font-bold text-[1.2rem] text-font-subtitle ">Promotores</h2>
            <p className=" font-bold text-[1.2rem] text-font-placeholder ">5</p>
          </div>
        </div>

        <div className=" flex justify-between items-center mb-10">
            <Title>Participantes</Title>
            <Button type='submit' size="default" color='default' icon={faArrowRight} onClick={()=>navigate("/admin/users/participantRegistration")}>Cadastrar</Button>
        </div>

        <table className=" w-full border-collapse mb-10 ">
          <thead>
            <tr className=" text-left bg-bg-white ">
              <th className=" py-4 pl-8 w-2/4 text-font-subtitle ">Nome</th>
              <th className=" py-4 text-font-subtitle ">CPF</th>
              <th colSpan={2} className=" py-4 text-font-subtitle ">Data de nascimento</th>
            </tr>
          </thead>
          <tbody>
            <tr className=" border-t-4 border-bg-dashboard my-2 bg-table-odd even:bg-table-even ">
              <td className=" py-2 pl-8 text-font-text ">
                <div className=" flex items-center ">
                  <div className=" absolute m-[-52px] bg-primary w-10 h-10 rounded-[50%] border-4 border-bg-dashboard flex justify-center items-center ">
                    <FontAwesomeIcon icon={faUser} className=' text-font-white text-[1rem]' />
                  </div>
                  Fulano de tal
                </div>
              </td>
              <td className=" py-2 text-font-text ">999.999.999-99</td>
              <td className=" py-2 text-font-text ">10/10/2000</td>
              <td className=" py-2 ">
                <div className=" flex justify-end mr-2 gap-2 ">
                  <div className=" bg-tertiary w-8 h-8 p-2 flex justify-center items-center rounded-md cursor-pointer ">
                    <FontAwesomeIcon icon={faPenToSquare} className=' text-font-white text-[1rem]' />
                  </div>
                  <div className=" bg-danger-400 w-8 h-8 p-2 flex justify-center items-center rounded-md cursor-pointer ">
                    <FontAwesomeIcon icon={faTrash} className=' text-font-white text-[1rem]' />
                  </div>
                </div>
              </td>
            </tr>
          </tbody>
        </table>

        <div className=" flex justify-between items-center mb-10">
            <Title>Atrações</Title>
            <Button type='submit' size="default" color='default' icon={faArrowRight} onClick={()=>navigate("/admin/users/attractionRegistration")}>Cadastrar</Button>
        </div>

        <table className=" w-full border-collapse mb-10 ">
          <thead>
            <tr className=" text-left bg-bg-white ">
              <th className=" py-4 pl-8 w-2/4 text-font-subtitle ">Nome</th>
              <th className=" py-4 text-font-subtitle ">CPF</th>
              <th colSpan={2} className=" py-4 text-font-subtitle ">Data de nascimento</th>
            </tr>
          </thead>
          <tbody>
            <tr className=" border-t-4 border-bg-dashboard my-2 bg-table-odd even:bg-table-even ">
              <td className=" py-2 pl-8 text-font-text ">
                <div className=" flex items-center ">
                  <div className=" absolute m-[-52px] bg-secondary w-10 h-10 rounded-[50%] border-4 border-bg-dashboard flex justify-center items-center ">
                    <FontAwesomeIcon icon={faUser} className=' text-font-white text-[1rem]' />
                  </div>
                  Fulano de tal
                </div>
              </td>
              <td className=" py-2 text-font-text ">999.999.999-99</td>
              <td className=" py-2 text-font-text ">10/10/2000</td>
              <td className=" py-2 ">
                <div className=" flex justify-end mr-2 gap-2 ">
                  <div className=" bg-tertiary w-8 h-8 p-2 flex justify-center items-center rounded-md cursor-pointer ">
                    <FontAwesomeIcon icon={faPenToSquare} className=' text-font-white text-[1rem]' />
                  </div>
                  <div className=" bg-danger-400 w-8 h-8 p-2 flex justify-center items-center rounded-md cursor-pointer ">
                    <FontAwesomeIcon icon={faTrash} className=' text-font-white text-[1rem]' />
                  </div>
                </div>
              </td>
            </tr>
          </tbody>
        </table>

        <div className=" flex justify-between items-center mb-10">
            <Title>Promotores</Title>
            <Button type='submit' size="default" color='default' icon={faArrowRight} onClick={()=>navigate("/admin/users/promoterRegistration")}>Cadastrar</Button>
        </div>

        <table className=" w-full border-collapse mb-10 ">
          <thead>
            <tr className=" text-left bg-bg-white ">
              <th className=" py-4 pl-8 w-2/4 text-font-subtitle ">Nome</th>
              <th className=" py-4 text-font-subtitle ">CPF</th>
              <th colSpan={2} className=" py-4 text-font-subtitle ">Data de nascimento</th>
            </tr>
          </thead>
          <tbody>
            <tr className=" border-t-4 border-bg-dashboard my-2 bg-table-odd even:bg-table-even ">
              <td className=" py-2 pl-8 text-font-text ">
                <div className=" flex items-center ">
                  <div className=" absolute m-[-52px] bg-tertiary w-10 h-10 rounded-[50%] border-4 border-bg-dashboard flex justify-center items-center ">
                    <FontAwesomeIcon icon={faUser} className=' text-font-white text-[1rem]' />
                  </div>
                  Fulano de tal
                </div>
              </td>
              <td className=" py-2 text-font-text ">999.999.999-99</td>
              <td className=" py-2 text-font-text ">10/10/2000</td>
              <td className=" py-2 ">
                <div className=" flex justify-end mr-2 gap-2 ">
                  <div className=" bg-tertiary w-8 h-8 p-2 flex justify-center items-center rounded-md cursor-pointer ">
                    <FontAwesomeIcon icon={faPenToSquare} className=' text-font-white text-[1rem]' />
                  </div>
                  <div className=" bg-danger-400 w-8 h-8 p-2 flex justify-center items-center rounded-md cursor-pointer ">
                    <FontAwesomeIcon icon={faTrash} className=' text-font-white text-[1rem]' />
                  </div>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
        
        <Alert status={status} visible={visible} setVisible={setVisible} title={title.toString()}>
            <p className='text-justify text-xs pl-4 mt-2 text-font-text'>{message}</p>
        </Alert>
    </>
  )
}
