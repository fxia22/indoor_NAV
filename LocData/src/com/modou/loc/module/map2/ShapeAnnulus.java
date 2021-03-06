package com.modou.loc.module.map2;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import com.modou.utils.MLog;

import android.content.Context;
import android.opengl.GLES20;

/**
 * @author 作者 E-mail: xyylchq@163.com
 * @version 创建时间: 2014-9-14 上午11:23:02
 * 类说明:
 * 圆环形状类
 */
public class ShapeAnnulus extends GraphicsObject {

	public ShapeAnnulus(Context ctx) {
		super(ctx);
	}

	//重写父类方法，根据扇环的6个点计算弧线轨迹上的所有点
	@Override
	protected void addPoints(List<Point> points) {
		if (points == null || points.size() < 6) {
			String errorStr = "绘制圆环时points数据不合法";
			if (MLog.DEBUG)
				throw new RuntimeException(errorStr);
			else
				MLog.e(errorStr);
			return;
		}
		
		Point p0 = points.get(0);
		Point p1 = points.get(1);
		Point p2 = points.get(2);
		Point p3 = points.get(3);
		Point p4 = points.get(4);
		Point p5 = points.get(5);
		Point centerP1 = MathUtils.computeCirclePoint(p0, p1, p2);
		Point centerP2 = MathUtils.computeCirclePoint(p3, p4, p5);
		
		List<Point> ps1 = MathUtils.computeCirlePoint(centerP1, p0, p1, p2);
		List<Point> ps2 = MathUtils.computeCirlePoint(centerP2, p3, p4, p5);
		
		
//		pointList.add(centerP1);
//		pointList.add(p0);
//		pointList.add(p1);
//		pointList.add(p2);
		
		pointList.addAll(ps1);
		
		// 这里要处理成倒序添加
		int sizeP = ps2.size();
		Point temp = null;
		for (int i = sizeP-1; i >= 0; i--) {
			temp = ps2.get(i);
			pointList.add(temp);
		}
		
		initPointArrs();
	}
	
	@Override
	protected void initVertexData() {
		super.initVertexData();
		
		vCount = pointArr.length / 3;
		// 创建顶点坐标数据缓冲
		ByteBuffer vbb = ByteBuffer.allocateDirect(pointArr.length * 4);
		vbb.order(ByteOrder.nativeOrder()); // 设置字节顺序
		mVertexBuffer = vbb.asFloatBuffer(); // 转换为Float型缓冲
		mVertexBuffer.put(pointArr);		// 向缓冲区中放入顶点坐标数据
		mVertexBuffer.position(0); // 设置缓冲区起始位置
		
		// 顶点颜色值数组，每个顶点4个色彩值RGBA
		int count = 0;
        float colors[]=new float[vCount*4];
        colors[count++] = 0; 
        colors[count++] = 0; 
        colors[count++] = 0; 
        colors[count++] = 0;
        for(int i=4; i<colors.length; i+=4){
        	colors[count++] = 1; 
        	colors[count++] = 0; 
        	colors[count++] = 0; 
        	colors[count++] = 0;
        }
		// 创建顶点着色数据缓冲
		ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length * 4);
		cbb.order(ByteOrder.nativeOrder()); // 设置字节顺序
		mColorBuffer = cbb.asFloatBuffer(); // 转换为Float型缓冲
		mColorBuffer.put(colors); // 向缓冲区中放入顶点着色数据
		mColorBuffer.position(0); // 设置缓冲区起始位置
	}
	
	@Override
	public void Draw(GL10 gl) {
		if (mVertexBuffer == null)
			return;
		
		// 制定使用某套shader程序
		GLES20.glUseProgram(mProgram);
		
		// 将最终变换矩阵传入shader程序
		GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, 
				MatrixState.getFinalMatrix(), 0);
		// 为画笔指定顶点位置数据
		GLES20.glVertexAttribPointer(maPositionHandle, 3, GLES20.GL_FLOAT, 
						false, 3 * 4, mVertexBuffer);
		// 为画笔指定顶点着色数据
		GLES20.glVertexAttribPointer(maColorHandle, 4, GLES20.GL_FLOAT, false,
				4 * 4, mColorBuffer);
//		GLES20.glUniform4fv(maColorHandle, 1, colors, 0);
		// 允许顶点位置数据数组
		GLES20.glEnableVertexAttribArray(maPositionHandle);
		GLES20.glEnableVertexAttribArray(maColorHandle);
		
		GLES20.glLineWidth(5);//设置线的宽度
		
		GLES20.glDrawArrays(GLES20.GL_LINE_LOOP, 0, vCount);
	}

}
