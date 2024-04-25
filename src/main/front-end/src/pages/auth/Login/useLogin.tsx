import React from "react"
import useStore from "stores/user"

import { useForm, SubmitHandler } from "react-hook-form"
import { useNavigate } from "react-router-dom"
import { ERROR_MESSAGE, validatePass } from "utils/regexValidate"
import { toast } from "react-toastify"
import { login } from "api/auth"

type FormData ={
    email: string, 
    password: string
}
const useLogin = () => {
    const {handleSubmit, control} = useForm<FormData>() 
    const [disable, setDisable] = React.useState(false)
    const [openModal, setOpenModal] = React.useState(false)
    const [qrCode, setQrCode] = React.useState('')
    const [email, setEmail] = React.useState('');
    const navigate = useNavigate();
    const {setDataUser} = useStore();

    const handleClose =() =>{
        setOpenModal(false)
    }

    const handleLogin: SubmitHandler<FormData> = async (data) =>{
        if(!validatePass(data.password)){
            toast.error(ERROR_MESSAGE.password)
            return
        }
        setDisable(true)
        const res = await toast.promise(login(data.email, data.password), {pending: 'Waiting for login'})
        console.log(res.data);
        if(res.status === 200){
            setOpenModal(true)
            setQrCode(res?.data?.data)
            setEmail(data.email)
            toast.success("Please verify 2 factor!")
        }
        else if(res.status === 403){
            toast.error(res.data.message) 
            navigate("/verify-email")
        }
        else if(res.status === 401){
            toast.error(res.data.message) 
            console.log("object");
            navigate("/change-password")
        }
        else
        {
            toast.error(res.data.message)
        }
        setDisable(false)
        
    }
    return {qrCode, email, openModal, setOpenModal, disable, control, handleSubmit, handleLogin, handleClose}
}

export default useLogin