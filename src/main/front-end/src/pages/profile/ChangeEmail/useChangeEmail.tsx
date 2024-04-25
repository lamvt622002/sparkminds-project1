import React from "react";
import { SubmitHandler, useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import userStore from "stores/user";
import { toast } from "react-toastify";
import { changeEmail } from "api/user";
import { CONFIG } from "common/config";
import { ERROR_MESSAGE, validatePass } from "utils/regexValidate";

type FormData ={
    password:string,
    email:string
}
const useChangeEmail = () =>{
    const { user, setDataUser } = userStore()
    const navigate = useNavigate()
    const { handleSubmit, control } = useForm<FormData>()
    const [disabled, setDisabled] = React.useState(false)

    const handleChangeEmail: SubmitHandler<FormData> = async (data) => {
      if(!validatePass(data.password)){
        toast.error(ERROR_MESSAGE.password)
        return
    }
        if (data.email === user?.email) {
          toast.error('New email must be different from the current email')
          return
        }
        setDisabled(true)
    
        const res = await toast.promise(changeEmail(data.password, data.email), {
          pending: 'Waiting for request change email...',
        })
        if (res.status === 204) {
          toast.success("Please verify your new email to change")
          navigate(CONFIG.PAGE_URLS.VERIFY_EMAIL)
        } else {
          toast.error(res.data.message)
        }
        setDisabled(false)
    } 
    return{user,control, disabled, handleChangeEmail, navigate, handleSubmit} 

}
export default useChangeEmail;