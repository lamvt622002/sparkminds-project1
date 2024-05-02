import React from "react"
import { useForm, SubmitHandler } from "react-hook-form"

import { useNavigate } from "react-router-dom"
import { toast } from "react-toastify"
import { validatePass } from "utils/regexValidate"
import { ERROR_MESSAGE } from "utils/regexValidate"

import { register } from "api/auth"
import { IResponse } from "types/IResponse"

type FormData = {
    first_name:string,
    last_name:string,
    birth_day:string,
    phone_number:string,
    email:string,
    password:string,
    confirm_password:string

}
const useRegister = () => {
    const navigate = useNavigate();
    const {handleSubmit, control, reset} = useForm<FormData>() 
    const [disable, setDisable] = React.useState(false);

    const handleRegister: SubmitHandler<FormData> = async (data) =>{
        if(data.password !== data.confirm_password){
            toast.error("Password and confirm password must be the same")
            return
        }
        if(!validatePass(data.password)){
            toast.error(ERROR_MESSAGE.password)
            return
        }

        setDisable(true)
        const res = await toast.promise(register(data.first_name, data.last_name, data.birth_day, data.phone_number, data.email, data.password, data.confirm_password),{
            pending:"waiting for regiser",
        })
        if(res.status === 204){
            toast.success("Register successfully!")
            navigate(`/verify-email/${data.email}`)
        }
        else{
            if(Array.isArray(res.data.message)){
                res.data.message.forEach((message:string) => {
                    toast.error(message)
                })
            }
            toast.error(res.data.message);
        }

        setDisable(false)
    }
    return {disable, control, handleSubmit, handleRegister}

}
export default useRegister