// See LICENSE for license details.

package example

import chisel3._

class FullAdder extends Module {
  val io = IO(new Bundle {
    val in0  = Input(UInt(1.W))
    val in1  = Input(UInt(1.W))
    val cin  = Input(UInt(1.W))
    val sout = Output(UInt(1.W))
    val cout = Output(UInt(1.W))
  })

  val in0_xor_in1 = io.in0 ^ io.in1
  val in0_and_in1 = io.in0 & io.in1
  val in0_and_cin = io.in0 & io.cin
  val in1_and_cin = io.cin & io.in1
  io.cout := in0_and_in1 | in0_and_cin | in1_and_cin
  io.sout := in0_xor_in1 ^  io.cin
}
