package org.firstinspires.ftc.teamcode.tele;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.RobotBase;

import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_TO_POSITION;

/**
 * Created by user on 12/9/17.
 */

@TeleOp(name = "DriveTest")

public class DriveTest extends RobotBase {

    @Override
    public void runOpMode() throws InterruptedException {
        setup();

        // Initialize glyph arm


        waitForStart();

        left_f.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        left_r.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        right_f.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        right_r.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lift_arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);


        while (opModeIsActive()) {
            // update


            int drop_position = 2500; //enter target value {through testing} of encoder position
            int original_position = 0; // original lift arm position


            //strafe
            double leftFront_Power = -2 * Math.cos(gamepad1.left_stick_y) * Math.sin(gamepad1.left_stick_x);
            double rightFront_Power = -leftFront_Power;
            double leftRear_Power = -leftFront_Power;
            double rightRear_Power = leftFront_Power;

            //drive
            double frontRight = gamepad1.right_stick_y;
            double rearRight = gamepad1.right_stick_y;
            double frontLeft = gamepad1.left_stick_y;
            double rearLeft = gamepad1.left_stick_y;


            if (gamepad2.x){

                climb(original_position);

            }
            else if(gamepad2.b){

                drop(drop_position);

            }

           /* while(gamepad2.right_bumper){

                extend();

            }

            while(gamepad2.left_bumper){

                retract();

            }

            double inPower = gamepad2.left_stick_y;
            intake(inPower);
*/



            telemetry.addData("leftfrontpower: ", leftFront_Power);
            telemetry.addData("leftrearpower: ", leftRear_Power);
            telemetry.addData("rightfrontpower: ", rightFront_Power);
            telemetry.addData("rightrearpower: ", rightRear_Power);
            telemetry.addData("lift position", lift_arm.getCurrentPosition());

            drive(leftFront_Power, rightFront_Power, leftRear_Power, rightRear_Power);
            drive(-frontLeft, -frontRight, -rearLeft, -rearRight);
            telemetry.update();
            idle();


            telemetry.update();
            idle();
        }
    }


    private void drive(double lf, double rf, double lr, double rr1){

        left_f.setPower(lf);
        right_f.setPower(rf);
        left_r.setPower(lr);
        right_r.setPower(rr1);

    }

   private void drop(int p){

        lift_arm.setTargetPosition(p);
        lift_arm.setPower(0.7);

    }

    private void climb(int o){

        lift_arm.setTargetPosition(o);
        lift_arm.setPower(0.7);

    }

/*   private void extend(){

        e_l.setPower(0.7);
        e_r.setPower(0.7);

    }

    private void retract(){

        e_l.setPower(-0.7);
        e_r.setPower(-0.7);

    }

    private void intake(inP){

        left_in.setPower(inP);
        right_in.setPower(inP);

    }

*/

}

