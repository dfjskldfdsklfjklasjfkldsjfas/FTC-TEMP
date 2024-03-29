/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.ServoController;


/**
 * This file contains an example of an iterative (Non-Linear) "OpMode".
 * An OpMode is a 'program' that runs in either the autonomous or the teleop period of an FTC match.
 * The names of OpModes appear on the menu of the FTC Driver Station.
 * When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all iterative OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="drop is motor", group="Iterative Opmode")

public class Basic extends OpMode {
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private DcMotor lift = null;
    private DcMotor drop = null;
    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        leftDrive = hardwareMap.get(DcMotor.class, "left_drive");
        rightDrive = hardwareMap.get(DcMotor.class, "right_drive");
        lift = hardwareMap.get(DcMotor.class, "lift");
        drop = hardwareMap.get(DcMotor.class, "drop");

        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.FORWARD);
        lift.setDirection(DcMotor.Direction.FORWARD);
        drop.setDirection(DcMotor.Direction.FORWARD);
        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     *
     */
    double leftPower;
    double rightPower;
    double liftPower;
    double dropPower;
    int control = 0;
    @Override
    public void start() {
        runtime.reset();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        // Setup a variable for each drive wheel to save power level for telemetry

        //double pos;
        //pos = 0;
        // Choose to drive using either Tank Mode, or POV Mode
        // Comment out the method that's not used.  The default below is POV.

        // POV Mode uses left stick to go forward, and right stick to turn.
        // - This uses basic math to combine motions and is easier to drive straight.
        if(gamepad1.a == true){
            control = 1;
        }
        if(gamepad2.a == true){
            control = 2;
        }
        if(gamepad2.b == true){
            control = 0;
        }if(gamepad1.b == true){
            control = 0;
        }
        if(control == 1){
            double turn = gamepad1.left_stick_x;
            double drive = gamepad1.right_trigger - gamepad1.left_trigger;

            leftPower = Range.clip(turn + drive, -1.0, 1.0);
            rightPower = Range.clip(turn - drive, -1.0, 1.0);
            liftPower = gamepad1.right_stick_y;
            if (gamepad1.dpad_down == true){
                drop.setPower(-.02);
            }
            if (gamepad1.dpad_up == true){
                drop.setPower(.02);
            }
            if (gamepad1.x == true){
                drop.setPower(0);
            }
            leftDrive.setPower(leftPower);
            rightDrive.setPower(rightPower);
            lift.setPower(liftPower);

        }
        else if(control == 2){
            double turn = gamepad2.left_stick_x;
            double drive = gamepad2.right_trigger - gamepad2.left_trigger;

            leftPower = Range.clip(turn + drive, -1.0, 1.0);
            rightPower = Range.clip(turn - drive, -1.0, 1.0);
            liftPower = gamepad2.right_stick_y;
            if (gamepad2.dpad_down == true){
                drop.setPower(-.02);
            }
            if (gamepad2.dpad_up == true){
                drop.setPower(.02);
            }
            if (gamepad2.x == true){
                drop.setPower(0);
            }
            leftDrive.setPower(leftPower);
            rightDrive.setPower(rightPower);
            lift.setPower(liftPower);
        }
        else if(control == 0) {
            double turn = gamepad1.left_stick_x;
            double drive = gamepad1.right_trigger - gamepad1.left_trigger;

            leftPower = Range.clip(turn + drive, -1.0, 1.0);
            rightPower = Range.clip(turn - drive, -1.0, 1.0);
            liftPower = gamepad2.right_stick_y;
            dropPower = gamepad2.left_stick_y;
            leftDrive.setPower(leftPower);
            rightDrive.setPower(rightPower);
            lift.setPower(liftPower);
            drop.setPower(.2 * dropPower);

            // Tank Mode uses one stick to control each wheel.
            // - This requires no math, but it is hard to drive forward slowly and keep straight.
            // leftPower  = gamepad1.right_trigger - gamepad1.left_trigger ;
            //rightPower = gamepad1.right_trigger - gamepad1.left_trigger ;
        /*if(gamepad2.a == true){
            pos = 90;
        }

*/
        }
        // Send calculated power to wheels
         telemetry.addData("control value", control);

        // Show the elapsed game time and wheel power.


        /*
         * Code to run ONCE after the driver hits STOP
         */
    }
}
