export enum Role{
  USER,
  ADMIN
}

export enum Status{
  ACTIVE,
  INACTIVE,
  LOCK,
  RESET_PASSWORD
}

export interface IUser {
  firstName: string,

  lastName:string

  email:string

  birthDay:Date

  phoneNumber:string

  role:Role

  status:Status

  accessToken:string

  refreshToken:string
  }