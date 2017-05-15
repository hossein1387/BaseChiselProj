// See LICENSE for license details.

package example.test

import chisel3.iotesters.{ChiselFlatSpec, Driver, PeekPokeTester}
import example.SimpleReg

class SimpleRegUnitTester(c: SimpleReg) extends PeekPokeTester(c) {

  private val sreg = c

  for(in <- 0 to 100 by 1) {
    poke(sreg.io.in , in)
    step(1)
    expect(sreg.io.out, in)
  }

}

class SimpleRegTester extends ChiselFlatSpec {
  private val backendNames = Array[String]("firrtl"/*, "verilator"*/)
  for ( backendName <- backendNames ) {
    "FullAdder" should s"Implement simple 32bit register (with $backendName)" in {
      Driver(() => new SimpleReg, backendName) {
        c => new SimpleRegUnitTester(c)
      } should be (true)
    }
  }
}
