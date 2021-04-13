/*
 * Team 4909, Bionics
 * Billerica Memorial High School
 *
 * Copyright:
 *   2021 Bionics
 *
 * License:
 *   MIT: https://opensource.org/licenses/MIT
 *   See the LICENSE file in the project's top-level directory for details.
 */

package frc.peyton;

import frc.bionic.swerve.AbstractDrivetrain;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.kauailabs.navx.frc.*;

public class Drivetrain extends AbstractDrivetrain {
  public SwerveModule   swerveRF; // right front
  public SwerveModule   swerveLF; // left front
  public SwerveModule   swerveLR; // left rear
  public SwerveModule   swerveRR; // right rear

  private AHRS navX;

  public Drivetrain() {
    double             kHalfWheelBaseWidthInches = 15.0;
    double             kHalfWheelBaseLengthInches = 15.0;

    swerveRF = new SwerveModule(1, 2, 0,  "RF", "Peyton");
    swerveLF = new SwerveModule(3, 4, 2,  "LF", "Peyton");
    swerveLR = new SwerveModule(5, 6, 4,  "LR", "Peyton");
    swerveRR = new SwerveModule(7, 8, 6,  "RR", "Peyton");

    this.initialize(swerveRF, swerveLF, swerveLR, swerveRR,
                    kHalfWheelBaseWidthInches, kHalfWheelBaseLengthInches);

    navX = new AHRS(SerialPort.Port.kMXP);
    navX.reset();
    SmartDashboard.putBoolean("NavX Reset", false);
  }

  public void periodic() {
    super.periodic();
    
    SmartDashboard.putData("NavX", navX);
    SmartDashboard.putNumber("Gyro Angle", this.getGyroAngle());

    if (SmartDashboard.getBoolean("NavX Reset", false)) {
      SmartDashboard.putBoolean("NavX Reset", false);
      navX.reset();
    }
  }

  // abstract superclass implementation
  public double getGyroAngle(){
    // negate result because NavX returns degrees measured clockwise from zero,
    // whereas the defined interface that this method implements states that
    // the method must return degrees measured counterclockwise from zero.
    return -navX.getAngle();
  }
}
