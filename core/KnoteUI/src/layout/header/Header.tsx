import {FC} from "react";
import HeaderProps from "@/layout/header/header.props";
import styles from "./header.module.css";
import BasicComponent from "@/component/basicComponent/BasicComponent";
import Link from "next/link";
import {IconButton, Typography} from "@mui/material";
import MenuIcon from '@mui/icons-material/Menu';

const Header:FC<HeaderProps> = ({className, style, onSiderSwitchClick, ...props}) => {
    return(
        <BasicComponent className={`${styles.main} ${className}`} style={style} {...props}>
            <IconButton size='large' className={styles.siderClosable} onClick={onSiderSwitchClick}>
                <MenuIcon className={styles.siderClosableIcon}/>
            </IconButton>

            <Link href={'/'} className={styles.homeHolder}>
                <Typography variant="h1">
                    K
                </Typography>

                <Typography variant='h5'>
                    Note
                </Typography>
            </Link>

            <Link href={'/profile'} className={styles.profileHolder}>
                <Typography variant='h5'>
                    profile
                </Typography>
            </Link>
        </BasicComponent>
    )
}


export default Header