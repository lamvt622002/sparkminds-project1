import { ButtonAuth } from "styles/Button";
import { Box, Grid, TextField, Typography } from "@mui/material";
import {Controller} from 'react-hook-form';

import styles from 'pages/auth/Auth.module.scss';
import useRegister from "./useRegister";
const Register = () => {
    const {disable, control, handleSubmit, handleRegister} = useRegister();
    return (
        <Box className={styles.layoutAuth}>
            <Box className={styles.mainLayout}>
                <Typography variant="h3" className={styles.formHeader}>
                    Register
                </Typography>
                <form onSubmit={handleSubmit(handleRegister)}>
                    <Grid container spacing={2}>
                        <Grid item xs={12}>
                            <Controller
                                name="first_name"
                                defaultValue={""}
                                control={control}
                                render={({field: {onChange, value}}) => (
                                    <TextField
                                        required
                                        name="first_name"
                                        placeholder="First name"
                                        type="text"
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
                                name="last_name"
                                defaultValue={""}
                                control={control}
                                render={({field: {onChange, value}}) => (
                                    <TextField
                                        required
                                        name="last_name"
                                        placeholder="Last name"
                                        type="text"
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
                                name="birth_day"
                                defaultValue={""}
                                control={control}
                                render={({field: {onChange, value}}) => (
                                    <TextField
                                        required
                                        name="birth_day"
                                        placeholder="yyyy-MM-dd"
                                        type="text"
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
                                name="phone_number"
                                defaultValue={""}
                                control={control}
                                render={({field: {onChange, value}}) => (
                                    <TextField
                                        required
                                        name="phone_number"
                                        placeholder="Phone number"
                                        type="text"
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
                            <Controller
                                name="confirm_password"
                                defaultValue={""}
                                control={control}
                                render={({field: {onChange, value}}) => (
                                    <TextField
                                        required
                                        name="confirm_password"
                                        placeholder="Confirm password"
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
                                Register
                            </ButtonAuth>
                        </Grid>
                        <Grid item xs={12} className={styles.authLink}>
                            <Typography variant="body2">
                                <a href="/login" className={styles.link}>
                                    Already have an account
                                </a>
                            </Typography>
                            <Typography variant="body2">
                                <a href="/forgot-password" className={styles.link}>
                                    Forgot password
                                </a>
                            </Typography>
                        </Grid>
                    </Grid>
                </form>
            </Box>
        </Box>
    )
}
export default Register