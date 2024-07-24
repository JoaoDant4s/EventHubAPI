import { ComponentProps } from 'react';

export type TitleProps = ComponentProps<'h2'>

export default function Title({children, className, ...props}:TitleProps) {


  return (
    <h2 className=' text-font-title font-bold text-[1.6rem] py-1 border-l-8 border-primary pl-4 ' {...props} >
      {children}
    </h2>
  )
}
