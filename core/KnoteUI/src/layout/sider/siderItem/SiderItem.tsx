import {FC} from "react";
import SiderItemProps from "@/layout/sider/siderItem/siderItem.props";
import {MenuItem} from "@mui/material";
import styles from './siderItem.module.css';


const SiderItem:FC<SiderItemProps> = ({ className, style, children,...props }) => {
    return (
        <MenuItem style={style} className={`${styles.container} ${className}`}>
            {children}
        </MenuItem>
    )
}

export default SiderItem;