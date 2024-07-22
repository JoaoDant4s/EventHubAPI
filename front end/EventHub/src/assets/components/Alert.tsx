
import { FontAwesomeIcon, FontAwesomeIconProps } from '@fortawesome/react-fontawesome'
import { IconDefinition } from '@fortawesome/free-solid-svg-icons';
import { ComponentProps, useEffect, useState } from 'react';
import { twMerge } from 'tailwind-merge';

export enum Status {
    Success="success",
    Info="info",
    Alert="alert",
    Danger="danger",
}

export type AlertProps = ComponentProps<'div'> & {
    icon:IconDefinition
    status:Status
    title:String
} 

export default function Alert({children, status = Status.Success, title, className, ...props}:AlertProps) {
    const [active, setActive] = useState<boolean>(false)

    const closeAlert=()=>{
        setActive(true)
    }

    return (
        <div
            className={
            twMerge(
                ` ${status == Status.Success?' bg-success-50 border-success-500':''} ${status == Status.Alert?' bg-alert-50 border-alert-500':''} ${status == Status.Danger?'bg-danger-50 border-danger-500':''} ${status == Status.Info?' bg-info-50 border-info-500 ':''} border-l-8 w-[400px]  absolute box-border top-0 right-0 p-4 m-8 rounded-md shadow-md cursor-pointer select-none `,
                className
            )}
            onClick={()=>closeAlert}
        >
            <div className=' flex flex-col'>
                <div className={` flex items-center ${status == Status.Success?' text-success-700':''} ${status == Status.Alert?' text-alert-700':''} ${status == Status.Danger?'text-danger-700':''} ${status == Status.Info?' text-info-700 ':''} `}>
                    <FontAwesomeIcon icon={props.icon} className=' text-sm absolute' />
                    <h4 className=' pl-6 font-bold'>{title}</h4>
                </div>
                {children}
            </div>
        </div>
    )
}