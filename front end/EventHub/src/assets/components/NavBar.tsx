import { ComponentProps, useEffect, useState } from 'react';
import { faList, faUser, faCreditCard, faTicket, faMapLocationDot, IconDefinition } from "@fortawesome/free-solid-svg-icons";
import { faArrowRightFromBracket } from '@fortawesome/free-solid-svg-icons';
import NavButton from './NavButton';
import { useNavigate } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

export type NavbarProps = ComponentProps<'nav'> & {
    name?:String
    role?:String
}

interface NavItem {
    icon:IconDefinition;
    color:"default"|"transparency";
    path:String;
    text:String;
}

export default function NavBar({ name = "Username", role = "Participant", className, ...props}:NavbarProps) {

    const navigate = useNavigate();
    const [navItem, setNavItem] = useState<Array<NavItem>>();

    const logout=()=>{
        localStorage.removeItem("token");
        if (localStorage.getItem("ROLE_ADMIN"))localStorage.removeItem("ROLE_ADMIN");
        if (localStorage.getItem("ROLE_PROMOTER"))localStorage.removeItem("ROLE_PROMOTER");
        if (localStorage.getItem("ROLE_ATTRACTION"))localStorage.removeItem("ROLE_ATTRACTION");
        if (localStorage.getItem("ROLE_USER"))localStorage.removeItem("ROLE_USER");
        navigate("/");
    }


    const navItemList = new Array;
    useEffect(()=>{
        const navItemList = new Array<NavItem>;

        if(localStorage.getItem("ROLE_USER") === "ROLE_USER"){
            navItemList.push({icon:faList,color:"default",path:"/dashboard",text:"Dashboard"});
            navItemList.push({icon:faUser,color:"transparency",path:"/dashboard",text:"Perfil"});
            navItemList.push({icon:faTicket,color:"transparency",path:"/dashboard",text:"Meus ingressos"});
            navItemList.push({icon:faCreditCard,color:"transparency",path:"/dashboard",text:"Cartão de crédito"});  
        }
        if(localStorage.getItem("ROLE_ADMIN") === "ROLE_ADMIN"){
            navItemList.push({icon:faList,color:"transparency",path:"/dashboard",text:"Usuário"});
            navItemList.push({icon:faList,color:"transparency",path:"/dashboard",text:"Eventos"});
        }
        if(localStorage.getItem("ROLE_PROMOTER") === "ROLE_PROMOTER"){
            navItemList.push({icon:faList,color:"default",path:"/dashboard",text:"Meus eventos"});
            navItemList.push({icon:faUser,color:"transparency",path:"/dashboard",text:"Perfil"});
        }
        if(localStorage.getItem("ROLE_ATTRACTION") === "ROLE_ATTRACTION"){
            navItemList.push({icon:faMapLocationDot,color:"transparency",path:"/dashboard",text:"Eventos participados"});
        }
        setNavItem(navItemList);

    }, [])

  return (
    <nav className=' bg-bg-white w-[360px] h-[100vh] shadow-xl absolute left-0 p-8 flex flex-col' {...props} >
        <div className='flex justify-center'>
            <h1 className=' text-font-title font-extrabold text-[2rem] my-10 '>EVENTHUB</h1>
        </div>
        <div className=' flex flex-col flex-grow gap-4 overflow-auto'>
            <div className={' pl-3 flex items-center text-bg-white font-bold rounded-md text-font-navItem'}>
                <div className=' w-10 h-10 flex justify-center items-center bg-font-navItem rounded-[50%] p-2'>
                    <FontAwesomeIcon icon={faUser} className=' text-font-placeholder ' />
                </div>
                <div className=' ml-3 flex-col justify-between '>
                    <h3>{name}</h3>
                    <p className=' text-font-text font-normal text-[0.8rem] ' >{role}</p>
                </div>
            </div>
            {navItem && navItem.map((item, index)=>{
                return (<NavButton key={`item-${index}`} icon={item.icon} color={item.color}>{item.text}</NavButton>)
            })}
        </div>
        <div>
            <NavButton onClick={()=>logout()} icon={faArrowRightFromBracket} color='transparency'>Sair</NavButton>
        </div>
    </nav>
  )
}
