import {FC} from "react";
import SiderProps from "@/layout/sider/sider.props";
import styles from "./sider.module.css"
import BasicComponent from "@/component/basicComponent/BasicComponent";


const Sider:FC<SiderProps> = ({ style  , ...props }) => {
    return(
        <BasicComponent className={styles.main} style={style} {...props}>
            IM_A_SIDER)))
        </BasicComponent>
    )
}

export default Sider