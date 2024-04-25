import APIClient from "api/apiClient";
import {CONFIG} from "common/config";

const baseApi = process.env.REACT_APP_API_ENDPOINT

export const login = (email: string, password: string) => {
  return APIClient.post(baseApi + CONFIG.ENDPOINT_API.AUTH.LOGIN, { email, password })
}
export const register = (first_name:string, last_name:string, birth_day:string, phone_number:string, email:string, password: string, confirm_password:string) => {
  return APIClient.post(baseApi + CONFIG.ENDPOINT_API.AUTH.REGISTER, 
    {
      firstName: first_name,
      lastName: last_name,
      birthDay: birth_day,
      phoneNumber: phone_number,
      email: email,
      password: password,
      confirmPassword: confirm_password
  })
}


export const forgotPassowrd = (email:string) => {
  return APIClient.post(baseApi + CONFIG.ENDPOINT_API.AUTH.FORGOT_PASSWORD, {email: email})
}

export const changePassword = (email:string, oldPassword:string, newPassword:string) => {
  return APIClient.post(baseApi + CONFIG.ENDPOINT_API.AUTH.CHANGE_PASSWORD, 
  {
    email:email,
    oldPassword:oldPassword,
    newPassword:newPassword
  })
}

export const test = () => {
  return APIClient.get(baseApi + "setCookie");
}
