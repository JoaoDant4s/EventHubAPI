import { ComponentProps } from 'react';
import { twMerge } from 'tailwind-merge';
import { tv, VariantProps } from 'tailwind-variants'
import { faList } from "@fortawesome/free-solid-svg-icons";
import { faArrowRightFromBracket } from '@fortawesome/free-solid-svg-icons';
import NavButton from './NavButton';
import { useNavigate } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faUser } from '@fortawesome/free-solid-svg-icons';

export type NavbarProps = ComponentProps<'nav'> & {
    name?:String
    role?:String
}

export default function NavBar({ name = "Username", role = "Participant", className, ...props}:NavbarProps) {

    const navigate = useNavigate();

    const logout=()=>{
        localStorage.removeItem("token");
        if (localStorage.getItem("ROLE_ADMIN"))localStorage.removeItem("ROLE_ADMIN");
        if (localStorage.getItem("ROLE_PROMOTER"))localStorage.removeItem("ROLE_PROMOTER");
        if (localStorage.getItem("ROLE_PROMOTER"))localStorage.removeItem("ROLE_PROMOTER");
        if (localStorage.getItem("ROLE_USER"))localStorage.removeItem("ROLE_USER");
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
            <NavButton icon={faList} color='default' >Dashboard</NavButton>
            <NavButton icon={faList} color='transparency'>Perfil</NavButton>
            <NavButton icon={faList} color='transparency'>Meus ingressos</NavButton>
            <NavButton icon={faList} color='transparency'>Cartão de crédito</NavButton>
        </div>
        <div>
            <NavButton onClick={()=>logout()} icon={faArrowRightFromBracket} color='transparency'>Sair</NavButton>
        </div>
    </nav>
  )
}
