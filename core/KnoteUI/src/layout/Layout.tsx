'use client'

import {FC, useState} from "react";
import LayoutProps from "@/layout/layout.props";
import Header from "@/layout/header/Header";
import Sider from "@/layout/sider/Sider";
import styles from "./layout.module.css";
import BasicComponent from "@/component/basicComponent/BasicComponent";

const Layout: FC<LayoutProps> = ({ children }) => {
    const headerHeight: number = 100;
    const [currentSiderWidthPercentage, setCurrentSiderWidthPercentage] = useState(15);

    const handleSiderSwitch = () => {
        setCurrentSiderWidthPercentage(currentSiderWidthPercentage === 0 ? 15 : 0);
    }

    return (
        <BasicComponent style={{height: 'inherit'}}>
            <BasicComponent className={styles.headerSiderContainer}>
                <Sider style={{width: currentSiderWidthPercentage + '%'}} headerHeight={headerHeight}/>
                <Header style={{height: headerHeight}} onSiderSwitchClick={handleSiderSwitch}/>
            </BasicComponent>

            <BasicComponent style={{width: '100%-' + currentSiderWidthPercentage + '%', height: 'calc(100%-' + headerHeight + 'px)',
                left: currentSiderWidthPercentage + '%', top: headerHeight}} className={styles.contentContainer}>
                {children}
            </BasicComponent>
        </BasicComponent>
    )
}

export default Layout;