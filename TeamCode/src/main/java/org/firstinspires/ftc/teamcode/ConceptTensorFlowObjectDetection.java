//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.firstinspires.ftc.robotcontroller.external.samples;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import java.util.Iterator;
import java.util.List;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.Parameters;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

@TeleOp(
        name = "Concept: TensorFlow Object Detection",
        group = "Concept"
)
//@Disabled
public class ConceptTensorFlowObjectDetection extends LinearOpMode {
    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";
    private static final String VUFORIA_KEY = "AZQi+D//////AAABmSTpnKGsQkpkmkp6PilbABgRB415R++JlIWe7z+q5EC+rfgh5mFdahBWATD85ohcwoj38eUHDeLxpV7t+DNfB4sfsub3haH/zmyh4vtypfNga6LeUVCMOk5yaH3F4YXoPvIwYSnzZK6UO7isjauFYxrmRNTLW4UPBDi4UueZRiYfe99IpMQ69cHMg9yE8UIViw8lqvNpzrrHYByH3zSZGv9o4Vmp+3Ls8ZZIKeXYNbIWDZiLwSwPmh96AVJ5lUF4RzOBvqZ18ozNAzOX3jCHtaMUPDAUJmjTV6RWkTikJuEE+EBE18xM6raodNlGx15sJdMHIaaPoG+qJHoPUV5pf3a5vDCaRzlAJM9M0RR6Irxg ";
    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;

    public ConceptTensorFlowObjectDetection() {
    }

    public void runOpMode() {
        this.initVuforia();
        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            this.initTfod();
        } else {
            this.telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }

        this.telemetry.addData(">", "Press Play to start tracking");
        this.telemetry.update();
        this.waitForStart();
        if (this.opModeIsActive()) {
            if (this.tfod != null) {
                this.tfod.activate();
            }

            label76:
            while(true) {
                List updatedRecognitions;
                do {
                    do {
                        if (!this.opModeIsActive()) {
                            break label76;
                        }
                    } while(this.tfod == null);

                    updatedRecognitions = this.tfod.getUpdatedRecognitions();
                } while(updatedRecognitions == null);

                this.telemetry.addData("# Object Detected", updatedRecognitions.size());
                if (updatedRecognitions.size() == 3) {
                    int goldMineralX = -1;
                    int silverMineral1X = -1;
                    int silverMineral2X = -1;
                    Iterator var5 = updatedRecognitions.iterator();

                    while(var5.hasNext()) {
                        Recognition recognition = (Recognition)var5.next();
                        if (recognition.getLabel().equals("Gold Mineral")) {
                            goldMineralX = (int)recognition.getLeft();
                        } else if (silverMineral1X == -1) {
                            silverMineral1X = (int)recognition.getLeft();
                        } else {
                            silverMineral2X = (int)recognition.getLeft();
                        }
                    }

                    if (goldMineralX != -1 && silverMineral1X != -1 && silverMineral2X != -1) {
                        if (goldMineralX < silverMineral1X && goldMineralX < silverMineral2X) {
                            this.telemetry.addData("Gold Mineral Position", "Left");
                        } else if (goldMineralX > silverMineral1X && goldMineralX > silverMineral2X) {
                            this.telemetry.addData("Gold Mineral Position", "Right");
                        } else {
                            this.telemetry.addData("Gold Mineral Position", "Center");
                        }
                    }
                }

                this.telemetry.update();
            }
        }

        if (this.tfod != null) {
            this.tfod.shutdown();
        }

    }

    private void initVuforia() {
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
        parameters.vuforiaLicenseKey = "AZQi+D//////AAABmSTpnKGsQkpkmkp6PilbABgRB415R++JlIWe7z+q5EC+rfgh5mFdahBWATD85ohcwoj38eUHDeLxpV7t+DNfB4sfsub3haH/zmyh4vtypfNga6LeUVCMOk5yaH3F4YXoPvIwYSnzZK6UO7isjauFYxrmRNTLW4UPBDi4UueZRiYfe99IpMQ69cHMg9yE8UIViw8lqvNpzrrHYByH3zSZGv9o4Vmp+3Ls8ZZIKeXYNbIWDZiLwSwPmh96AVJ5lUF4RzOBvqZ18ozNAzOX3jCHtaMUPDAUJmjTV6RWkTikJuEE+EBE18xM6raodNlGx15sJdMHIaaPoG+qJHoPUV5pf3a5vDCaRzlAJM9M0RR6Irxg";
        parameters.cameraDirection = CameraDirection.BACK;
        this.vuforia = ClassFactory.getInstance().createVuforia(parameters);
    }

    private void initTfod() {
        int tfodMonitorViewId = this.hardwareMap.appContext.getResources().getIdentifier("tfodMonitorViewId", "id", this.hardwareMap.appContext.getPackageName());
        org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector.Parameters tfodParameters = new org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector.Parameters(tfodMonitorViewId);
        this.tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, this.vuforia);
        this.tfod.loadModelFromAsset("RoverRuckus.tflite", new String[]{"Gold Mineral", "Silver Mineral"});
    }
}
