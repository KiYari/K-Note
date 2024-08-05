import {FC} from "react";
import SiderProps from "@/layout/sider/sider.props";
import styles from "./sider.module.css"
import BasicComponent from "@/component/basicComponent/BasicComponent";
import {MenuList} from "@mui/material";
import SiderItem from "@/layout/sider/siderItem/SiderItem";


const Sider:FC<SiderProps> = ({ style  , headerHeight, isSiderOpened, siderWidth,  ...props }) => {

    const combinedStyle = {
        width: siderWidth,
        left: isSiderOpened ? 0 : -siderWidth + '%',
        ...style
    }
    return(
        <BasicComponent className={styles.main} style={combinedStyle} {...props}>
            <MenuList dense style={{top: headerHeight}}>
                <SiderItem>
                    abc
                </SiderItem>
                <SiderItem>
                    abc2323
                </SiderItem>
                <SiderItem>
                    abc4444
                </SiderItem>
                <SiderItem>
                    abc56611
                </SiderItem>
            </MenuList>
        </BasicComponent>
    )
}

export default Sider