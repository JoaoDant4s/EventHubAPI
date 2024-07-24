import { ComponentProps, ReactNode } from 'react';
import { faArrowRightFromBracket, faUser, IconDefinition } from '@fortawesome/free-solid-svg-icons';
import NavButton from './NavButton';
import { useLocation, useNavigate } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

export type NavbarProps = ComponentProps<'nav'> & {
    name?:String
    role?:String
    items?:Array<NavItem>
}

export interface NavItem {
    text:String;
    icon:IconDefinition;
    path:String;
    component:ReactNode;
}

export default function NavBar({ name = "Username", role = "Participant", items, className, ...props}:NavbarProps) {
    const location = useLocation();
    const navigate = useNavigate();

    const logout=()=>{
        localStorage.removeItem("token");
        if (localStorage.getItem("ROLE_ADMIN"))localStorage.removeItem("ROLE_ADMIN");
        if (localStorage.getItem("ROLE_PROMOTER"))localStorage.removeItem("ROLE_PROMOTER");
        if (localStorage.getItem("ROLE_ATTRACTION"))localStorage.removeItem("ROLE_ATTRACTION");
        if (localStorage.getItem("ROLE_USER"))localStorage.removeItem("ROLE_USER");
        if (localStorage.getItem("login"))localStorage.removeItem("login");
        navigate("/");
    }

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
            {items && items.map((item, index)=>{
                return (<NavButton key={`item-${index}`} icon={item.icon} color={item.path === location.pathname?"default":"transparency"} onClick={()=>navigate(item.path.toString())}>{item.text}</NavButton>)
            })}
        </div>
        <div>
            <NavButton onClick={()=>logout()} icon={faArrowRightFromBracket} color='transparency'>Sair</NavButton>
        </div>
    </nav>
  )
}
