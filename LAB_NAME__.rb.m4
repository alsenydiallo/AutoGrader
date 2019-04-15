require "AssessmentBase.rb"

module LAB_NAME_RUBY__
  include AssessmentBase

  def assessmentInitialize(course)
    super("LAB_NAME__",course)
    @problems = []
  end

end
