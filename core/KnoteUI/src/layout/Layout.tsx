'use client'

import {FC, useState} from "react";
import LayoutProps from "@/layout/layout.props";
import Header from "@/layout/header/Header";
import Sider from "@/layout/sider/Sider";
import styles from "./layout.module.css";
import BasicComponent from "@/component/basicComponent/BasicComponent";

const Layout: FC<LayoutProps> = ({ children }) => {
    const headerHeight: number = 100;
    const SIDER_WIDTH = 250;
    const [isSiderOpened, setIsSiderOpened] = useState(true);

    const handleSiderSwitch = () => {
        setIsSiderOpened(!isSiderOpened);
    }

    return (
        <BasicComponent style={{height: 'inherit', overflowX: 'clip'}}>
            <BasicComponent className={styles.headerSiderContainer}>
                <Sider siderWidth={SIDER_WIDTH} isSiderOpened={isSiderOpened} headerHeight={headerHeight} style={{top: 100, height: 'calc(100vh - ' + headerHeight + 'px)'}}/>
                <Header style={{height: headerHeight}} onSiderSwitchClick={handleSiderSwitch}/>
            </BasicComponent>

            <BasicComponent style={{height: 'calc(100vh-' + headerHeight + 'px)',
                left: isSiderOpened ? SIDER_WIDTH : 0, top: headerHeight}} className={styles.contentContainer}>
                {children}
            </BasicComponent>
        </BasicComponent>
    )
}

export default Layout;