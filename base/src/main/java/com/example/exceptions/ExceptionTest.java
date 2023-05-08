package com.example.exceptions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

//自定义异常
//一般受检异常就继承Exception
//一般运行时异常就继承RuntimeException
public class ExceptionTest extends Exception{

    private String msg;

    public ExceptionTest(String msg) {
        super(msg);//最好super，因为这样可以借用父类的方法，就不用自己写了
        this.msg = msg;
    }

    //方法主动抛出异常，交由调用者处理
    public static void main(String[] args) throws IOException, ExceptionTest {
        //异常处理try catch finally
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("test.txt");
            fis.read();//读
        } catch (FileNotFoundException e) {//找不到文件
            e.getMessage();//获取异常信息
            e.printStackTrace();//获取堆栈信息
        } catch (IOException e) {//IO出错，IOE比FNFE大
            e.printStackTrace();
        }finally {
            if(fis != null){
                fis.close();
            }
        }

        //手动抛出异常对象，一般用于某些异常场景，且此时不需要返回
        if(1 > 0){
            throw new ExceptionTest("123");
        }

        //try-with-resource，自动关闭流
        //里面可以写多个
        try (FileInputStream fiss = new FileInputStream("text.txt"); FileOutputStream fos = new FileOutputStream("")){

        }catch (Exception e){

        }

    }

}
