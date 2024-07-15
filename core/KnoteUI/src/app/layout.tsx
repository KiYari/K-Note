import type { Metadata } from "next";
import { Inter } from "next/font/google";
import "./globals.css";
import Layout from '../layout/Layout';

const inter = Inter({ subsets: ["latin"] });

export const metadata: Metadata = {
  title: "K-Note",
  description: "K-Note app",
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en">
      <body className={inter.className} style={{height: 'inherit'}}>
      <Layout>
        {children}
      </Layout>
      </body>
    </html>
  );
}
