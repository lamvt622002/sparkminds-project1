export const REGEX_INPUT = {
    phone:
      /^((\+\d{1,3}(-| )?\(?\d\)?(-| )?\d{1,3})|(\(?\d{2,3}\)?))(-| )?(\d{3,4})(-| )?(\d{4})(( x| ext)\d{1,5}){0,1}$/,
    email: /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/,
    password: /^(?=.*[a-z])(?=.*[A-Z])(?=.*[\W_]).{6,}$/,
  }
  
  export const ERROR_MESSAGE = {
    phone: 'Invalid phone number',
    email: 'Invalid email address',
    password:
      'Password must have at least 6 characters, contain at least 1 lowercase letter, 1 uppercase letter and 1 special character',
  }
  
  export const validatePass = function validatePassword(pass: string): boolean {
    const newPasswordTemp: string = pass
    const minNumberofChars: number = 6
    const regularExpression: RegExp = REGEX_INPUT.password
  
    if (newPasswordTemp.length < minNumberofChars) {
      return false
    }
  
    if (!regularExpression.test(newPasswordTemp)) {
      return false
    }
  
    return true
  }
  