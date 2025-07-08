var regRuleTranslateX = /translate(X|\dd)?\(\s*(\w+\s*,)?\s*([^,]+)(\s*,[^)]+)?\s*\)/

export function getTranslateX(node) {
  var regRule2 = /matrix\(.*,\s*(\w+)\s*\)/
  var transform = node.style.transform
  var reg
  if (!transform) {
    return null
  }
  reg = regRuleTranslateX.exec(transform)
  if (reg === null) {
    reg = regRule2.exec(transform)
    return reg ? reg[1] : null
  }
  return reg[3]
}
