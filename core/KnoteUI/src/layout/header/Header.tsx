import {FC} from "react";
import HeaderProps from "@/layout/header/header.props";
import styles from "./header.module.css";
import BasicComponent from "@/component/basicComponent/BasicComponent";
import Link from "next/link";
import {Typography} from "@mui/material";

const Header:FC<HeaderProps> = ({className, style, ...props}) => {
    return(
        <BasicComponent className={`${styles.main} ${className}`} style={style} {...props}>
            <Link href={'/'} className={styles.homeHolder}>
                <Typography variant="h5">
                    <Typography variant="h3">
                        K
                    </Typography>
                    Note
                </Typography>
            </Link>

            <Link href={'/profile'} className={styles.profileHolder}>
                profile
            </Link>
        </BasicComponent>
    )
}


export default Header