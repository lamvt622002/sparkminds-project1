import { Box, Card, CardContent, CardMedia, Typography } from "@mui/material";
import { CONFIG } from "common/config";
import styles from 'pages/auth/Auth.module.scss';
import { ButtonAuth } from "styles/Button";
import useVerifyEmail from "./useVerifyEmail";

const VerifyEmail = () => {
    const {disable, email, handleResentLink} = useVerifyEmail();
    return (
        <Box className={styles.verifyEmail}>
            <Box className={styles.body}>
                <Card className={styles.cardMain}>
                    <CardMedia
                        component="img"
                        className={styles.logo}
                        image={CONFIG.IMAGES.EMAIL_VERIFICATION}
                        alt="email verification image"
                    />
                    <CardContent className={styles.content}>
                        <Typography variant="h4">
                            Verify your email address
                        </Typography>
                        <Typography variant="body2">
                            You have entered {email} as the email address for your account.
                        </Typography>
                        <Typography variant="body2">
                            Please verify this email address by clicking button below.
                        </Typography>
                        <ButtonAuth variant="contained" disabled={disable}>
                            <a href="https://mail.google.com/mail/u/0/" className={styles.link}> Verify your email</a>
                        </ButtonAuth>
                        <ButtonAuth variant="contained" disabled={disable} onClick={handleResentLink}>
                            Don't receive eamil. Resent
                        </ButtonAuth>
                    </CardContent>
                </Card>
               
            </Box>
        </Box>
    )
}
export default VerifyEmail;