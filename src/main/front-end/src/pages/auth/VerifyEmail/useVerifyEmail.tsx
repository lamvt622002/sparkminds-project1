import React from "react"
import useStore from "stores/user"

import { useForm, SubmitHandler } from "react-hook-form"
import { useNavigate } from "react-router-dom"
import { toast } from "react-toastify"
import { forgotPassowrd } from "api/auth"

type FormData ={
    email: string
}
const useVerifyEmail = () => {
    const {handleSubmit, control} = useForm<FormData>() 
    const [disable, setDisable] = React.useState(false)
    const navigate = useNavigate();

    const handleForgotPassword: SubmitHandler<FormData> = async (data) =>{
       
        setDisable(true)
        const res = await toast.promise(forgotPassowrd(data.email), {pending: 'Waiting for forgot password'})

        if(res.status === 204){
            toast.success("Sent resquest successfully")
            navigate("/verify-email")
        }
        else
        {
            toast.error(res.data.message)
        }
        setDisable(false)
        
    }
    return {disable, control, handleSubmit, handleForgotPassword}
}

export default useVerifyEmail