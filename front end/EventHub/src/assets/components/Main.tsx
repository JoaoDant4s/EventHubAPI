import { ComponentProps } from 'react';

export type MainProps = ComponentProps<'main'>

export default function Container({children, className, ...props}:MainProps) {


  return (
    <main className=' py-20 ' {...props} >
      {children}
    </main>
  )
}
