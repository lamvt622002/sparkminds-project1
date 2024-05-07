
import React from "react";
import { SubmitHandler, useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import userStore from "stores/user";
import { toast } from "react-toastify";
import { changePhone } from "api/user";
import { CONFIG } from "common/config";
import { ERROR_MESSAGE, validatePass } from "utils/regexValidate";

type FormData ={
    password:string,
    phoneNumber:string
}
const useChangePhone = () =>{
    const { user, setDataUser } = userStore()
    const navigate = useNavigate()
    const { handleSubmit, control } = useForm<FormData>()
    const [disabled, setDisabled] = React.useState(false)
    const [openModal, setOpenModal] = React.useState(false)

    const handleChangePhone: SubmitHandler<FormData> = async (data) => {
        if(!validatePass(data.password)){
            toast.error(ERROR_MESSAGE.password)
            return
        }
        if (data.phoneNumber === user?.phoneNumber) {
          toast.error('Phone number must be different')
          return
        }
        setDisabled(true)
    
        const res = await toast.promise(changePhone(data.password, data.phoneNumber), {
          pending: 'Waiting for request change phone',
        })
        if (res.status === 204) {
          toast.success("Please enter code to change phone")
          setOpenModal(true)
        } else {
          toast.error(res.data.message)
        }
        setDisabled(false)
    } 
    const handleClose = () => {
      setOpenModal(false)
    }
    return{user,control, disabled, handleChangePhone, navigate, handleSubmit, handleClose, openModal, setOpenModal} 
}
export default useChangePhone;