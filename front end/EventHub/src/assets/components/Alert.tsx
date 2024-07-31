import { ComponentProps } from 'react';
import { twMerge } from 'tailwind-merge';


export type Status = "success" | "info" | "alert" | "danger" | String;

type AlertProps = ComponentProps<'div'> & {
    status?:Status;
    title:String;
    visible?:boolean;
    setVisible?:React.Dispatch<React.SetStateAction<boolean>>
} 

export const setAlert=(message:String, status:Status, visible:boolean)=>{
    
    localStorage.setItem("alertMessage", message.toString());
    localStorage.setItem("alertStatus", status.toString());
    localStorage.setItem("alertVisible",visible?"true":"false");
    localStorage.setItem("alertTitle", getTitle(status));
}

export const getAlert=(
    setMessage:React.Dispatch<React.SetStateAction<String>>,
    setStatus:React.Dispatch<React.SetStateAction<Status>>,
    setVisible:React.Dispatch<React.SetStateAction<boolean>>,
    setTitle:React.Dispatch<React.SetStateAction<String>>
)=>{
    
    if(localStorage.getItem("alertMessage") !== null) {
        let localMessage = localStorage.getItem("alertMessage") || "";
        setMessage(localMessage);
        localStorage.removeItem("alertMessage");
      }
      if(localStorage.getItem("alertStatus") !== null) {
        let localStatus = localStorage.getItem("alertStatus") || "danger";
        setStatus(localStatus);
        localStorage.removeItem("alertStatus");
      }
      if(localStorage.getItem("alertVisible") === "true") {
        let localVisible = true;
        setVisible(localVisible);
        localStorage.removeItem("alertVisible");
      }
      if(localStorage.getItem("alertTitle") !== null) {
        let localTitle = localStorage.getItem("alertTitle") || "";
        setTitle(localTitle);
        localStorage.removeItem("alertTitle");
      }
}

const getTitle=(status:Status)=>{
    if(status.toString() === "alert"){
        return "Cuidado!"
    } else if(status.toString() === "danger"){
        return "Erro!"
    } else if(status.toString() === "info"){
        return "Informações!"
    } else if(status.toString() === "success"){
        return "Sucesso!"
    } else {
        return "Sucesso!"
    }
}

export default function Alert({children, status = "success", visible = false, setVisible=()=>{}, className, ...props}:AlertProps) {

    const closeAlert=(e:React.MouseEvent<HTMLDivElement, MouseEvent>)=>{
        if(e.currentTarget.classList.contains("animate-openModal")){
            e.currentTarget.classList.remove("animate-openModal")
            e.currentTarget.classList.add("animate-closeModal")
        } else if(e.currentTarget.classList.contains("animate-closeModal")) {
            e.currentTarget.classList.remove("animate-closeModal")
            e.currentTarget.classList.add("animate-openModal")
        }
        setTimeout(()=>{
            setVisible(false);
        }, 500)
    }

    return (
        <div
            className={
            twMerge(
                ` ${status == "success"?' bg-success-50 border-success-500':''} ${status == "alert"?' bg-alert-50 border-alert-500':''} ${status == "danger"?'bg-danger-50 border-danger-500':''} ${status == "info"?' bg-info-50 border-info-500 ':''} border-l-8 w-[400px]  absolute box-border top-0 right-0 p-4 m-8 rounded-md shadow-md cursor-pointer select-none ${visible==true?'':'hidden opacity-0'} `,
                className
            )}
            onClick={(e)=>closeAlert(e)}
        >
            <div className=' flex flex-col'>
                <div className={` flex items-center ${status == "success"?' text-success-700':''} ${status == "alert"?' text-alert-700':''} ${status == "danger"?'text-danger-700':''} ${status == "info"?' text-info-700 ':''} `}>
                    <h4 className=' pl-4 font-bold'>{props.title}</h4>
                </div>
                {children}
            </div>
        </div>
    )
}