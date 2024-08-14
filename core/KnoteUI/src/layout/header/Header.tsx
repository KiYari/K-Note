import {FC} from "react";
import HeaderProps from "@/layout/header/header.props";
import styles from "./header.module.css";
import BasicComponent from "@/component/basicComponent/BasicComponent";
import Link from "next/link";
import {Button, Typography} from "antd";
import {MenuOutlined} from "@ant-design/icons";

const { Title } = Typography;

const Header:FC<HeaderProps> = ({className, style, onSiderSwitchClick, ...props}) => {
    return(
        <BasicComponent className={`${styles.main} ${className}`} style={style} {...props}>
            <Button size='large' className={styles.siderClosable} onClick={onSiderSwitchClick} icon={<MenuOutlined className={styles.siderClosableIcon}/>}>
            </Button>

            <Link href={'/'} className={styles.homeHolder}>
                <Title level={1}>
                    K
                </Title>

                <Title level={5}>
                    Note
                </Title>
            </Link>

            <Link href={'/profile'} className={styles.profileHolder}>
                <Title level={5}>
                    profile
                </Title>
            </Link>
        </BasicComponent>
    )
}


export default Header