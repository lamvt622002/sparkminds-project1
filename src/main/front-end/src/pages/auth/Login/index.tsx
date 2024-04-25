import { ButtonAuth } from "styles/Button";
import useLogin from 'pages/auth/Login/useLogin';
import { Box, Grid, TextField, Typography } from "@mui/material";
import {Controller} from 'react-hook-form';

import styles from 'pages/auth/Auth.module.scss';
import GoogleAuthModel from "components/models/GoogleAuthModel";



const Login = () => {
    const {openModal, email, setOpenModal, handleClose ,qrCode,disable, control, handleLogin, handleSubmit} = useLogin();
    return(
        <Box className={styles.layoutAuth}>
            <Box className={styles.mainLayout}>
                <Typography variant="h3" className={styles.formHeader}>
                    Log in
                </Typography>
                <form onSubmit={handleSubmit(handleLogin)}>
                    <Grid container spacing={2}>
                        <Grid item xs={12}>
                            <Controller
                                name="email"
                                defaultValue={""}
                                control={control}
                                render={({field: {onChange, value}}) => (
                                    <TextField
                                        required
                                        name="email"
                                        placeholder="Email"
                                        type="email"
                                        fullWidth
                                        onChange={onChange}
                                        value={value}
                                        size="small"
                                    />
                                )}
                            />

                        </Grid>
                        <Grid item xs={12}>
                            <Controller
                                name="password"
                                defaultValue={""}
                                control={control}
                                render={({field: {onChange, value}}) => (
                                    <TextField
                                        required
                                        name="password"
                                        placeholder="Password"
                                        type="password"
                                        fullWidth
                                        onChange={onChange}
                                        value={value}
                                        size="small"
                                    />
                                )}
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <ButtonAuth fullWidth type="submit" variant="contained" disabled={disable}>
                                Login
                            </ButtonAuth>
                        </Grid>
                        <Grid item xs={12} className={styles.authLink}>
                            <Typography variant="body2">
                                <a href="/forgot-password" className={styles.link}>
                                    Forgot password
                                </a>
                            </Typography>
                            <Typography variant="body2">
                                <a href="/register" className={styles.link}>
                                    Dont have an account?
                                </a>
                            </Typography>
                        </Grid>
                    </Grid>
                </form>
                <GoogleAuthModel
                    open={openModal}
                    qrCode={qrCode}
                    email={email}
                    handleClose={handleClose}
                />
            </Box>
        </Box>
    )
}

export default Login