// See LICENSE for license details.

package example

import chisel3._

class SimpleReg extends Module {
  val io = IO(new Bundle {
    val in  = Input(UInt(32.W))
    val out = Output(UInt(32.W))
  })

  val reg = RegInit(0.U)
  reg    := io.in
  io.out := reg
}
